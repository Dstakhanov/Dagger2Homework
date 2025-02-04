package ru.otus.daggerhomework

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FragmentReceiver : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactoryReceiver
    private val viewModelReceiver: ViewModelReceiver by viewModels { viewModelFactory }

    private lateinit var frame: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frame = view.findViewById(R.id.frame)
        lifecycleScope.launchWhenStarted {
            viewModelReceiver.color
                .onEach { populateColor(it) }
                .collect()
        }
    }

    fun populateColor(@ColorInt color: Int) {
        frame.setBackgroundColor(color)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFragmentReceiverComponent
            .factory()
            .create(
                (requireActivity().applicationContext as App).appComponent,
                (requireActivity() as MainActivity).activityComponent
            )
            .inject(this)
    }
}