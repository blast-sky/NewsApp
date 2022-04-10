package com.example.newsapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.FragmentArticleBinding
import com.squareup.picasso.Picasso
import com.example.newsapp.R
import com.example.newsapp.appComponent
import com.example.newsapp.ui.viewmodel.DatabaseViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import javax.inject.Inject


class ArticleFragment : Fragment() {

    @Inject
    lateinit var factory: DatabaseViewModel.Factory
    private val databaseViewModel by viewModels<DatabaseViewModel> { factory }

    private val args: ArticleFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentArticleBinding.bind(view)

        applyArticleToBinding(binding)
        handleFloatActionButton(binding.fab)
    }

    private fun handleFloatActionButton(fab: FloatingActionButton) {
        databaseViewModel.allArticles.observe(viewLifecycleOwner) { articles ->
            fab.backgroundTintList = when(articles.contains(args.article)) {
                true -> {
                    fab.setOnClickListener {
                        databaseViewModel.deleteArticle(args.article)
                        Toast.makeText(context, "Article deleted from bookmarks", Toast.LENGTH_SHORT)
                            .show()
                    }
                    ColorStateList.valueOf(resources.getColor(R.color.gray, context?.theme))
                }
                false -> {
                    fab.setOnClickListener {
                        databaseViewModel.insertArticle(args.article)
                        Toast.makeText(context, "Article inserted in bookmarks", Toast.LENGTH_SHORT)
                            .show()
                    }
                    ColorStateList.valueOf(resources.getColor(R.color.pink, context?.theme))
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun applyArticleToBinding(binding: FragmentArticleBinding) {
        val article = args.article
        binding.apply {
            if(article.urlToImage != "")
                Picasso.get().load(article.urlToImage).into(articleImage)
            articleAuthor.text = article.source.name
            val dateFormat = SimpleDateFormat("dd MMMM yyyy")
            articleData.text = dateFormat.format(article.publishedAt)
            articleTitle.text = article.title
            articleDescription.text = article.description
            onSiteLabel.setOnClickListener {
                findNavController().navigate(
                    R.id.action_articleFragment_to_webViewFragment,
                    Bundle().apply {
                        putString("url", article.url)
                        putString("fromFragment", args.fromFragment)
                    }
                )
            }
        }
    }
}