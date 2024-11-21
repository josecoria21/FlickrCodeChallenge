package dev.propoc.flickrcodechallenge.view.detailspage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dev.propoc.flickrcodechallenge.databinding.ImageDetailBinding
import dev.propoc.flickrcodechallenge.viewmodel.DetailViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailFragment : Fragment() {

    private lateinit var binding: ImageDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ImageDetailBinding.inflate(inflater, container, false).apply {
            this.viewModel = this@DetailFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val item = arguments?.let { DetailFragmentArgs.fromBundle(it).item }

        binding.item = item

        Glide.with(requireContext()).load(item?.link).into(binding.imageDetail)

        return binding.root
    }
}