package com.example.bundles

import android.content.Context
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.play.core.splitcompat.SplitCompat

abstract class BaseSplitFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SplitCompat.install(context)
    }


}