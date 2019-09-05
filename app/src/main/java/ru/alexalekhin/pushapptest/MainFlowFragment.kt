package ru.alexalekhin.pushapptest

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainFlowFragment : Fragment(R.layout.fragment_main_flow) {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFlowFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}
