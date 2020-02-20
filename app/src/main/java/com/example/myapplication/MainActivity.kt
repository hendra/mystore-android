package com.example.myapplication

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.Adapters.ProductAdapter
import com.example.myapplication.Models.Product
import com.example.myapplication.Services.ApiClient
import com.example.myapplication.Services.ProductApi
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private var products : ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        rvProducts.layoutManager = GridLayoutManager(this, 2)
        setUpRecyleView()

        //** Set the colors of the Pull To Refresh View
        swipeContainer.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        swipeContainer.setColorSchemeColors(Color.WHITE)

        swipeContainer.setOnRefreshListener {
            products.clear()
            setUpRecyleView()
            swipeContainer.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inFlater = menuInflater
        inFlater.inflate(R.menu.menu_search,menu)

        val manager = getSystemService(Context.SEARCH_SERVICE)as SearchManager
        val searchItem = menu!!.findItem(R.id.search_menu)
        val searchView = searchItem!!.actionView as SearchView
            searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    searchView.setQuery("", false)
                    searchItem.collapseActionView()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })
        return true

//        return super.onCreateOptionsMenu(menu)
    }

    fun setUpRecyleView() {

        val apiInterface : ProductApi = ApiClient.getClient().create(ProductApi::class.java)

        apiInterface.getProducts()
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    response.body()?.forEach {
//                        println("TAG_: ${it}")
                        products.add(it)
                    }
                    rvProducts.adapter = ProductAdapter(products)
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) = t.printStackTrace()
            })
    }
}
