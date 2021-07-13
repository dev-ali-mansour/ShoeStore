package com.udacity.shoestore.presentation.shoe_listing

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListingBinding
import com.udacity.shoestore.databinding.ListItemBinding
import com.udacity.shoestore.domain.model.Shoe

class ShoeListingFragment : Fragment() {
    private lateinit var binding: FragmentShoeListingBinding
    private val vieModel: ShoeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().title=getString(R.string.shoes_list)
        binding = FragmentShoeListingBinding.inflate(inflater, container, false)

        vieModel.shoes.observe(viewLifecycleOwner, { shoesList ->
            if (shoesList.isNotEmpty()) listShoes(shoesList)
        })
        binding.fabAddShoe.setOnClickListener {
            binding.root.findNavController()
                .navigate(
                    ShoeListingFragmentDirections
                        .actionShoeListingsToShoeDetails()
                )
        }

        return binding.root
    }

    private fun listShoes(shoes: List<Shoe>) {
        context?.let { context ->
            val shoeContainer = binding.container
            shoes.forEach { shoe ->
                val itemBinding: ListItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(context),
                    R.layout.list_item,
                    null,
                    false
                )
                itemBinding.lifecycleOwner = this
                itemBinding.shoeListItem = shoe
                itemBinding.root.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                shoeContainer.addView(itemBinding.root)
            }
        }
    }
}