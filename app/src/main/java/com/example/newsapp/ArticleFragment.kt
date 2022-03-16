package com.example.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso
import androidx.appcompat.app.AppCompatActivity




class ArticleFragment : Fragment() {

    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity?)?.supportActionBar?.title = args.article.source.name
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentArticleBinding.bind(view)
        val article = args.article

        binding.apply {
            Picasso.get().load(article.urlToImage).into(articleImage)
            articleAuthor.text = article.source.name
            articleData.text = article.publishedAt.toString()
            articleTitle.text = article.title
            articleDescription.text = article.description
            onSiteLabel.setOnClickListener {
                findNavController().navigate(
                    R.id.action_articleFragment_to_webViewFragment,
                    Bundle().apply {
                        putString("url", article.url)
                    }
                )
            }
        }

        super.onViewCreated(binding.root, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleFragment()
    }
}