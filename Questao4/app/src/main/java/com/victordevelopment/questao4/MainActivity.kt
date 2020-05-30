package com.victordevelopment.questao4

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Factory for the ViewModelProvider
        val factory: ViewModelProvider.Factory = NewInstanceFactory()

        setContentView(R.layout.activity_main)

        searchViewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)

        searchViewModel.movieList.observe(this, Observer { list ->
            updateList(list)
        })

        searchViewModel.list()

        view_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val item = view_list.adapter.getItem(position) as String
            Toast.makeText(this, item, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateList(list: List<String>?) {
        var adapter = view_list.adapter
        if (adapter == null) {
            adapter = SearchListAdapter(list, this@MainActivity)
            view_list.adapter = adapter
        } else {
            (adapter as SearchListAdapter).list = list
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        // Needs to be on to the close action work
        searchView.isIconifiedByDefault = true
        searchView.isSubmitButtonEnabled = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchViewModel.list()
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            /**
             * Handler to when the searchView is closed, so it can reload the movies list
             */
            override fun onClose(): Boolean {
                searchViewModel.list()
                return false
            }
        })
        return true
    }

    override fun onNewIntent(intent: Intent?) {
        if (intent != null) {
            handleIntent(intent)
        }
        super.onNewIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY) as String
            searchViewModel.filter(query)
        }

    }
}