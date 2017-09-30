package video.xdownloader.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import video.xdownloader.R
//import video.xdownloader.extractor.xpress.DownloadActivity

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [YouTubeDownloadFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [YouTubeDownloadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YouTubeDownloadFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    private var editText: EditText? = null

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



    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText = view!!.findViewById<EditText>(R.id.edittext);
        val button = view.findViewById<Button>(R.id.search_btn);
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(xpView: View?) {

//                val intent = Intent(context, DownloadActivity::class.java)
//                intent.setAction(Intent.ACTION_SEND)
//                intent.setType("text/plain")
//                intent.putExtra(Intent.EXTRA_TEXT, editText!!.text.toString())
//                val text = editText!!.text.toString();
//                if (text.isNotEmpty())
//                    startActivity(intent)
//                else
//                    Toast.makeText(context, "please enter your", Toast.LENGTH_LONG).show();
            }
        })

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment YouTubeDownloadFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): YouTubeDownloadFragment {
            val fragment = YouTubeDownloadFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
