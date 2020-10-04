package com.koleychik.currencyvalue.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.koleychik.currencyvalue.MainSingleton
import com.koleychik.currencyvalue.R
import com.koleychik.currencyvalue.additions.Convert
import com.koleychik.currencyvalue.additions.Keys
import com.koleychik.currencyvalue.additions.SharedPreferenceUtils
import com.koleychik.currencyvalue.model.Currencies
import com.koleychik.currencyvalue.model.Favorites
import com.koleychik.currencyvalue.ui.adapters.FavoriteAdapter
import com.koleychik.currencyvalue.ui.state.FavoriteState
import com.koleychik.currencyvalue.ui.viewModels.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class FavoriteFragment : Fragment() {

    private lateinit var root: View
    private lateinit var viewModel: FavoriteViewModel

    private lateinit var sharedPreferenceUtils: SharedPreferenceUtils

    private val adapter = FavoriteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_favorite, container, false)
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        sharedPreferenceUtils = SharedPreferenceUtils(root.context)

        makeRv()
        subscribe()

        root.swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = true
            getList()
        }

        return root
    }

    private fun subscribe() {
        viewModel.state.observe(viewLifecycleOwner, { render(it) })
    }

    private fun render(state: FavoriteState) {
        when (state) {
            is FavoriteState.Loading -> {
                root.rv.visibility = View.GONE
                root.text_havent_favorites.visibility = View.GONE
                root.progress_bar.visibility = View.VISIBLE
                getList()
            }
            is FavoriteState.NothingInDb -> {
                root.rv.visibility = View.GONE
                root.text_havent_favorites.visibility = View.VISIBLE
                root.progress_bar.visibility = View.GONE
            }
            is FavoriteState.Show -> {
                adapter.submitList(state.list, MainSingleton.getMainSingleton().listFavorite)

                root.rv.visibility = View.VISIBLE
                root.text_havent_favorites.visibility = View.GONE
                root.progress_bar.visibility = View.GONE
            }
        }
    }

    private fun getList(isRefreshing: Boolean = false) = CoroutineScope(Dispatchers.IO).launch {

        Log.d(Keys.APP, "getList")

        val listFavoritesId = MainSingleton.getMainSingleton().listFavorite
        val listCurrency = MainSingleton.getMainSingleton().list

        val listFavorite = mutableListOf<Favorites>()
        for (i in listFavoritesId) {
            Log.d(Keys.APP, "listFavoritesId i = $i")
            val id1 = i[0].toString().toInt()
            Log.d(Keys.APP, "id1 = $id1")
            val id2 = i[1].toString().toInt()
            Log.d(Keys.APP, "id2 = $id2")

            listFavorite.add(createFavorite(listCurrency[id1], listCurrency[id2], i))
        }

        withContext(Dispatchers.Main) {
//            if ()
            if (listFavorite.isEmpty()) {
                viewModel.state.value = FavoriteState.NothingInDb
            } else {
                viewModel.state.value = FavoriteState.Show(listFavorite)
            }
            root.swipeToRefresh.isRefreshing = false
        }
    }

    private fun createFavorite(currency1: Currencies, currency2: Currencies, id: String) =
        Favorites(
            id.toInt(),
            nameFirst = currency1.Name,
            nameSecond = currency2.Name,
            valueLastDay = sharedPreferenceUtils.getFloat(id),
            valueToday = Convert.convert(
                BigDecimal(1),
                BigDecimal(currency1.value),
                BigDecimal(currency2.value)
            ).toFloat()
        )

    private fun makeRv() {
        root.rv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getList()
    }
}