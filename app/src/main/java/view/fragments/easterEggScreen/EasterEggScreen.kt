package view.fragments.easternEggScreen

import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import com.example.gaiety.R
import view.fragments.easterEggScreen.VectorDrawableTagItem

class EasterEggScreen : Fragment() {
    companion object {
        fun newInstance(): EasterEggScreen =
            EasterEggScreen()

        var drawableResList = listOf(
            R.drawable.ic_example_1,
            R.drawable.ic_example_2,
            R.drawable.ic_example_3,
            R.drawable.ic_example_4,
            R.drawable.ic_example_5,
            R.drawable.ic_example_6,
            R.drawable.ic_example_7,
            R.drawable.ic_example_8,
            R.drawable.ic_example_9,
            R.drawable.ic_example_10,
            R.drawable.ic_example_11,
            R.drawable.ic_example_12,
            R.drawable.ic_example_13,
            R.drawable.ic_example_14,
            R.drawable.ic_example_15,
            R.drawable.ic_example_16,
            R.drawable.ic_example_17,
            R.drawable.ic_example_18,
            R.drawable.ic_example_19,
            R.drawable.ic_example_20,
            R.drawable.ic_example_21,
            R.drawable.ic_example_22,
            R.drawable.ic_example_23,
            R.drawable.ic_example_24,
            R.drawable.ic_example_25
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_easter_egg_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTagView()
    }

    private fun initTagView() {
        val tags = mutableListOf<VectorDrawableTagItem>()
        val tagSphereView = view?.findViewById<com.magicgoop.tagsphere.TagSphereView>(R.id.tagView)
        for(id in drawableResList){
            getVectorDrawable(id)?.let {
                tags.add(VectorDrawableTagItem(it))
            }
        }
        tagSphereView?.addTagList(tags)
        tagSphereView?.setRadius(2.75f)
    }

    private fun getVectorDrawable(id: Int): Drawable? =
        ContextCompat.getDrawable(requireContext(), id)

}


