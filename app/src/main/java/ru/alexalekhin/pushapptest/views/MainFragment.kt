package ru.alexalekhin.pushapptest.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.alexalekhin.pushapptest.OnFragmentInteractionListener
import ru.alexalekhin.pushapptest.R

class MainFragment : Fragment(R.layout.fragment_main) {
    private var listener: OnFragmentInteractionListener? = null

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
            MainFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}
