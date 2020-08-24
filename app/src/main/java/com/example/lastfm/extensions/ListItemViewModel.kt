package com.example.lastfm.extensions

import com.example.lastfm.extensions.GenericAdapter.OnListItemViewClickListener

//Generic viewModel class that is used with the generic adapter
abstract class ListItemViewModel {
    var adapterPosition: Int = -1
    var onListItemViewClickListener: OnListItemViewClickListener? = null
}
