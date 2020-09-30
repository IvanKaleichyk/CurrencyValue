package com.koleychik.currencyvalue.ui.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.koleychik.currencyvalue.MainSingleton
import com.koleychik.currencyvalue.R
import com.koleychik.currencyvalue.additions.*
import com.koleychik.currencyvalue.spinner.MakeSpinnerAdapter
import com.koleychik.currencyvalue.ui.state.ConvertState
import com.koleychik.currencyvalue.ui.viewModels.ConvertViewModel
import kotlinx.android.synthetic.main.fragment_convert.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.math.BigDecimal
import java.util.ArrayList

class ConvertFragment : Fragment() {

    private lateinit var root: View
    private lateinit var viewModel: ConvertViewModel

    private var num1 = 0
    private var num2 = 1

    private val mainSingleton = MainSingleton.getMainSingleton()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_convert, container, false)
        viewModel = ViewModelProvider(this)[ConvertViewModel::class.java]

        Log.d(Keys.APP, "Start Convert Fragment")

        getDataFromSPref()

        checkEdtToPutPoint()
        makeOnClick()
        makeSpinners()
        subscribe()

        return root
    }

    private fun subscribe() {
        viewModel.state.observe(viewLifecycleOwner, { render(it) })

        viewModel.isFavorite.observe(viewLifecycleOwner, {
            if (it){
                root.imgStar.setImageResource(R.drawable.star)
            }
            else{
                root.imgStar.setImageResource(R.drawable.star_dont_click)
            }
        })
    }

    private fun render(state: ConvertState) {
        when (state) {
            is ConvertState.Loading -> {
                root.progressBar.visibility = View.VISIBLE
                root.showLL.visibility = View.GONE
                loadData()
            }
            is ConvertState.Error -> {
                Toast.makeText(root.context, root.context.getText(state.textRes), Toast.LENGTH_LONG)
                    .show()
            }
            is ConvertState.Show -> {
                viewModel.list.value = state.list
                root.progressBar.visibility = View.GONE
                root.showLL.visibility = View.VISIBLE

            }
        }
    }

    private fun loadData() = CoroutineScope(Dispatchers.IO).launch {
        if (mainSingleton.list.isEmpty() || mainSingleton.list == null) {
            val parsingUkrBank = ParsingUkrBank()
            parsingUkrBank.start()
            mainSingleton.list = parsingUkrBank.currenciesList as ArrayList
        }

        withContext(Dispatchers.Main) {
            if (mainSingleton.list.isEmpty()) {
                viewModel.state.value = ConvertState.Error(R.string.cannotLoadInformation)
            } else {
//                mainSingleton.list = parsingUkrBank.currenciesList as ArrayList
                viewModel.state.value = ConvertState.Show(mainSingleton.list)
            }
        }
    }

    private fun makeOnClick() {
        root.imgStar.setOnClickListener {

            Log.d(Keys.APP, "click to imgStart")

            val id = WorkWithString.getStringID(num1, num2)
            Log.d(Keys.APP, "Convert Fragment id = $id")
            if (mainSingleton.listFavorite.contains(id)) {
                mainSingleton.listFavorite.remove(id)
                viewModel.isFavorite.value = false
            } else {
                viewModel.isFavorite.value = true
                mainSingleton.listFavorite.add(id)
            }
        }

        root.convert.setOnClickListener {
            val value2 = viewModel.list.value?.get(num1)!!.value
            val value3 = viewModel.list.value?.get(num2)!!.value

            var value1 = BigDecimal(0)

            try {
                value1 = BigDecimal(Convert.deleteAllSpace(root.number1.text.toString().trim()))
            } catch (e: Exception) { }

            root.number2.text = viewModel.putPoint(
                Convert.convert(
                    num = value1,
                    value1 = BigDecimal(value2),
                    value2 = BigDecimal(value3)
                )
            )

        }

    }

    private fun checkEdtToPutPoint() {
        root.number1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (viewModel.clickOnKeyBoard()) {
//                    edt.setText(viewModel.getPoint(edt.text.toString()))
                    Log.d("list_item", viewModel.putPoint(root.number1.text.toString()))
                    root.number1.setText(viewModel.putPoint(root.number1.text.toString()))
                    root.number1.setSelection(root.number1.text.length)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (viewModel.clickOnKeyBoard()) {
//                    edt.setText(viewModel.getPoint(edt.text.toString()))
                    Log.d("list_item", viewModel.putPoint(root.number1.text.toString()))
                    root.number1.setText(viewModel.putPoint(root.number1.text.toString()))
                    root.number1.setSelection(root.number1.text.length)
                }
            }
        })
    }

    private fun makeSpinners() {
        val spinnerAdapter =
            context?.let { MakeSpinnerAdapter(it, viewModel.makeListSpinnerModel()) }!!

        root.spnFirst.adapter = spinnerAdapter
        root.spnSecond.adapter = spinnerAdapter

        setSelectionsForSpn()
        root.spnFirst.onItemSelectedListener = onItemSelect1()
        root.spnSecond.onItemSelectedListener = onItemSelect2()
    }

    private fun setSelectionsForSpn() {
        viewModel.number1.observe(viewLifecycleOwner) {
            root.spnFirst.setSelection(it)
            num1 = it
        }
        viewModel.number2.observe(viewLifecycleOwner) {
            root.spnSecond.setSelection(it)
            num2 = it
        }
    }


    private fun onItemSelect1(): AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                root.number2.text = ""

                viewModel.number1.value = position
                viewModel.isFavorite.value = checkIfInListFavorite(
                    num1,
                    num2
                )
            }
        }

    private fun onItemSelect2(): AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                root.number2.text = ""

                viewModel.number2.value = position
                viewModel.isFavorite.value = checkIfInListFavorite(
                    num1,
                    num2
                )
            }
        }

    private fun checkIfInListFavorite(id1: Int, id2: Int): Boolean {
        val id = WorkWithString.getStringID(id1, id2)
        return mainSingleton.listFavorite.contains(id)
    }

    private fun getDataFromSPref() = CoroutineScope(Dispatchers.IO).launch {
        val sharedPreferenceUtils = SharedPreferenceUtils(root.context)
        val list = sharedPreferenceUtils.getListFavorites()

        if (list.isEmpty()) {
            mainSingleton.listFavorite = ArrayList()
        } else {
            mainSingleton.listFavorite = list as ArrayList
        }
    }

}