package com.lightstone.github.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.lightstone.github.R
import com.lightstone.github.databinding.FragmentDetailsBinding
import com.lightstone.github.model.response.RepoDetails
import com.lightstone.github.view.listener.ItemClickListener
import com.lightstone.github.viewmodel.viewmodelfactory.DetailsViewModelFactory
import com.lightstone.github.viewmodel.viewmodels.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class DetailsFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory : DetailsViewModelFactory by instance()

    private lateinit var viewModel : DetailsViewModel

    private lateinit var dataBinding : FragmentDetailsBinding

    private var username : String? = ""
    private var reponame : String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return dataBinding.root
        //return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBarDetails.visibility = View.VISIBLE

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DetailsViewModel::class.java)

        arguments?.let {
            DetailsFragmentArgs.fromBundle(it).reponame?.let {
                reponame = it
            }
            DetailsFragmentArgs.fromBundle(it).username?.let {
                username = it
            }
        }

        visit.setOnClickListener {
            dataBinding.repoDetails?.let { onVisitClicked(it) }
        }


        bindUi()
    }

    private fun bindUi() = launch {
        username?.let { user->
            reponame?.let { repo->
                viewModel.getRepo(user, repo)
            }

        }

        viewModel.repoDetails.observe(viewLifecycleOwner, Observer {
            progressBarDetails.visibility = View.GONE
            dataBinding.repoDetails = it
        })
    }

    fun onVisitClicked(repoDetails : RepoDetails) {
        val uri = Uri.parse(repoDetails.html_url)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(uri)
        startActivity(intent)
    }
}
