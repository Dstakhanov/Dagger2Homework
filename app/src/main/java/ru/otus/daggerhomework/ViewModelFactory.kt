package ru.otus.daggerhomework

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import ru.otus.daggerhomework.qualifiers.ActivityScope
import ru.otus.daggerhomework.qualifiers.ApplicationScope
import javax.inject.Inject

class ViewModelFactoryProducer @Inject constructor(
    private val colorGenerator: ColorGenerator,
    @ActivityScope private val context: Context,
    private val observer: MutableStateFlow<Int>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ViewModelProducer(colorGenerator, context, observer) as T
}

class ViewModelFactoryReceiver @Inject constructor(
    @ApplicationScope private val context: Context,
    private val observer: MutableStateFlow<Int>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ViewModelReceiver(context, observer) as T
}