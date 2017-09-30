package video.xdownloader.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import video.xdownloader.R
import video.xdownloader.adapters.GenericAdapterRecycler
import video.xdownloader.models.PixabayModel
import video.xdownloader.resetapi.RestAdapter
import video.xdownloader.utils.Utils
import com.bumptech.glide.Glide
import android.graphics.Bitmap
import android.opengl.Visibility
import android.os.AsyncTask
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_you_tube_download.*
import video.xdownloader.resetapi.JsonRequest
import video.xdownloader.resetapi.RestAdapter2
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ImageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView
    //    private var dataModel: PixabayModel = PixabayModel()
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_you_tube_download, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }


    private lateinit var searchBtn: ImageView

    private lateinit var noRecordFound: View
    private lateinit var progressCenter: ProgressBar

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText = view!!.findViewById<EditText>(R.id.edittext);
        recyclerView = view.findViewById<RecyclerView>(R.id.imagesRecyclerView);
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        searchBtn = view.findViewById<ImageView>(R.id.search_btn);
        noRecordFound = view.findViewById(R.id.no_record_found);
        progressCenter = view.findViewById(R.id.progress_bar_fragment);
        //todo add random keyword for everytime or evryday
        var listDays = ArrayList<String>();
        listDays.add("love")
        listDays.add("flowers")
        listDays.add("nature")
        listDays.add("couple")
        listDays.add("beautiful")
        listDays.add("joy")
        listDays.add("jewelery")
        listDays.add("diamond")
        listDays.add("fruit")
        listDays.add("baby")
        Collections.shuffle(listDays)
        requestApi(listDays[0])
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    searchBtn.visibility = View.INVISIBLE
                } else {
                    searchBtn.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        searchBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(xpView: View?) {
                requestApi(editText.text.toString())
            }
        })


    }

    private lateinit var progressDialog: ProgressDialog

    fun requestApi(request: String) {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("please wait....")
        progressDialog.show()

        RestAdapter2.getInstance().apiService.getPixabayData("6587476-10331a56a0cd1a3788020c469", request).enqueue(object : Callback<PixabayModel> {
            override fun onFailure(call: Call<PixabayModel>?, t: Throwable?) {
                Log.d("tag", "error")
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show()
                progressDialog.dismiss()

            }

            override fun onResponse(call: Call<PixabayModel>?, response: Response<PixabayModel>?) {
                progressDialog.dismiss()
                if (response != null && response.body() != null) {
                    if (response.body().hits?.size!! > 0) {
                        hideKeyboard()
                        recyclerView.visibility = View.VISIBLE
                        progressCenter.visibility = View.INVISIBLE
                        noRecordFound.visibility = View.INVISIBLE
                        adapter = Adapter(response.body().hits!!)
                        recyclerView.setAdapter(adapter)
                    } else {
                        recyclerView.visibility = View.INVISIBLE
                        noRecordFound.visibility = View.VISIBLE
                    }
                } else {
                    recyclerView.visibility = View.INVISIBLE
                    noRecordFound.visibility = View.VISIBLE
                }
            }

        })

    }


    fun hideKeyboard() {
        val view = editText
        if (context != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            mListener = context
//        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): ImageFragment {
            val fragment = ImageFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }


    class Adapter(private val hitList: List<PixabayModel.Hit>) : RecyclerView.Adapter<Adapter.ImageViewHolder>() {

        override fun getItemCount(): Int {
            return hitList.size
        }

        private lateinit var progressDialog: ProgressDialog

        override fun onBindViewHolder(holder: ImageViewHolder?, position: Int) {
//            holder?.imageView?.setTag(hitList.get(position))
            if (holder != null) {
                Utils.findViewAndLoadAnimatedImageUri(holder.imageView, hitList[position].webformatURL, false)
                holder.shareVIre?.setTag(position)
                holder.shareVIre?.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        val context = p0?.context;
                        var position = p0!!.tag
                        var imageUrl = hitList[position as Int].webformatURL
                        progressDialog = ProgressDialog(context)
                        progressDialog.setMessage("Preparing...")
                        progressDialog.show()
                        object : AsyncTask<Void, String, String>() {

                            override fun doInBackground(vararg voids: Void): String {
                                val theBitmap = Glide.with(p0?.context).load(imageUrl).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()
                                var bitmapPath = MediaStore.Images.Media.insertImage(p0.context.getContentResolver(), theBitmap, "title", null);
                                return bitmapPath

                            }

                            override fun onPreExecute() {
                                super.onPreExecute()

                            }

                            override fun onPostExecute(result: String?) {
                                super.onPostExecute(result)
                                if (context != null) {
                                    progressDialog.dismiss()
                                    var bitmapUri = Uri.parse(result);
                                    var intent = Intent(Intent.ACTION_SEND)
                                    intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                                    intent.setType("image/png");
                                    context!!.startActivity(intent)
                                }

                            }
                        }.execute()


                    }

                })

                holder.downloadText?.text = "Downloads " + hitList[position].downloads
                holder.downloadView?.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {

                    }

                })
            };
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ImageViewHolder {
            var inflater = LayoutInflater.from(parent?.context)
            var view = inflater.inflate(R.layout.image_item, parent, false);
            return ImageViewHolder(view)
        }


        class ImageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
            var imageView = itemView?.findViewById<ImageView>(R.id.image_view_item);
            var shareVIre = itemView?.findViewById<ImageView>(R.id.share_iv);
            var downloadView = itemView?.findViewById<ImageView>(R.id.download_iv);
            var downloadText = itemView?.findViewById<TextView>(R.id.download_tv);
        }
    }

}// Required empty public constructor