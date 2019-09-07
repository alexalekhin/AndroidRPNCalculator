package ru.alexalekhin.pushapptest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var viewModel: MainScreenViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainScreenViewModel::class.java]
        //Observers
        viewModel.expression.observe(this, Observer {
            textViewExpression.text = it
        })
        viewModel.result.observe(this, Observer {
            textViewResult.text = it
        })
        //Numbers
        button0.setOnClickListener { viewModel.addNumber(button0.text.toString()) }
        button1.setOnClickListener { viewModel.addNumber(button1.text.toString()) }
        button2.setOnClickListener { viewModel.addNumber(button2.text.toString()) }
        button3.setOnClickListener { viewModel.addNumber(button3.text.toString()) }
        button4.setOnClickListener { viewModel.addNumber(button4.text.toString()) }
        button5.setOnClickListener { viewModel.addNumber(button5.text.toString()) }
        button6.setOnClickListener { viewModel.addNumber(button6.text.toString()) }
        button7.setOnClickListener { viewModel.addNumber(button7.text.toString()) }
        button8.setOnClickListener { viewModel.addNumber(button8.text.toString()) }
        button9.setOnClickListener { viewModel.addNumber(button9.text.toString()) }
        //Operations
        buttonAdd.setOnClickListener { viewModel.addOperation(buttonAdd.text.toString()) }
        buttonSubtract.setOnClickListener { viewModel.addOperation(buttonSubtract.text.toString()) }
        buttonMultiply.setOnClickListener { viewModel.addOperation(buttonMultiply.text.toString()) }
        buttonDivide.setOnClickListener { viewModel.addOperation(buttonDivide.text.toString()) }
        buttonPercent.setOnClickListener { viewModel.addOperation(buttonPercent.text.toString()) }
        buttonSign.setOnClickListener { viewModel.addSign() }
        buttonComma.setOnClickListener { viewModel.addComma() }
        //Actions
        buttonAC.setOnClickListener { viewModel.clearAll() }
        buttonResult.setOnClickListener { viewModel.calculate() }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
