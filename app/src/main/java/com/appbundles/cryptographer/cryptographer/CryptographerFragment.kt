package com.appbundles.cryptographer.cryptographer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appbundles.cryptographer.*

class CryptographerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_cryptographer, container, false)
        val fm = childFragmentManager
        val ft = fm.beginTransaction()
        if (savedInstanceState == null) {
            val caeserFragment= CaeserFragment()
            ft.replace(R.id.caeserFragmentContainer, caeserFragment, TAG_CAESER_FRAGMENT)
            val affineFragment = AffineFragment()
            ft.replace(R.id.affineFragmentContainer, affineFragment, TAG_AFFINE_FRAGMENT)
            val vigenereFragment = VigenereFragment()
            ft.replace(R.id.vigenereFragmentContainer, vigenereFragment, TAG_VIGENERE_FRAGMENT)
            val playfairFragment = PlayfairFragment()
            ft.replace(R.id.playfairFragmentContainer, playfairFragment, TAG_PLAYFAIR_FRAGMENT)
            val transpositionalFragment = TranspositionalFragment()
            ft.replace(
                R.id.orthogonalFragmentContainer,
                transpositionalFragment,
                TAG_TRANSPOSITIONAL_FRAGMENT
            )
            ft.commit()
        } else {
            childFragmentManager.findFragmentByTag(TAG_CAESER_FRAGMENT)
            childFragmentManager.findFragmentByTag(TAG_AFFINE_FRAGMENT)
            childFragmentManager.findFragmentByTag(TAG_VIGENERE_FRAGMENT)
            childFragmentManager.findFragmentByTag(TAG_PLAYFAIR_FRAGMENT)
            childFragmentManager.findFragmentByTag(TAG_TRANSPOSITIONAL_FRAGMENT)
        }
        return v


    }

    companion object {
        const val TAG_CAESER_FRAGMENT = "caeser fr"
        const val TAG_AFFINE_FRAGMENT = "affine fr"
        const val TAG_VIGENERE_FRAGMENT = "vigenere fr"
        const val TAG_PLAYFAIR_FRAGMENT = "playfair fr"
        const val TAG_TRANSPOSITIONAL_FRAGMENT = "trans fr"
    }
}
