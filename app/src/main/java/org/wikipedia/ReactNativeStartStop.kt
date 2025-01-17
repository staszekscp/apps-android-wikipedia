package org.wikipedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.facebook.react.ReactFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReactNativeStartStop.newInstance] factory method to
 * create an instance of this fragment.
 */

class ReactNativeStartStop : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_react_native_start_stop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the screen width and height
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        // Find the fragment container view explicitly with the type
        val fragmentContainer: FrameLayout = view.findViewById<FrameLayout>(R.id.fragment_container)

        // Set the layout parameters to 50% of the screen width and height
        val layoutParams = fragmentContainer.layoutParams
        layoutParams.width = screenWidth / 2  // Set width to 50% of screen width
        layoutParams.height = screenHeight / 2  // Set height to 50% of screen height
        fragmentContainer.layoutParams = layoutParams

        // Set up the start and stop buttons
        val startButton: Button = view.findViewById(R.id.start_button)
        val stopButton: Button = view.findViewById(R.id.stop_button)
        val stopWithInvalidateButton: Button = view.findViewById(R.id.stop_with_invalidate_button)

        // Display Placeholder fragment by default
        stopButton.isEnabled = false
        stopWithInvalidateButton.isEnabled = false
        showPlaceholderFragment()

        startButton.setOnClickListener {
            // TO DO: Implement start button callback
            println("Start button clicked!")
            stopButton.isEnabled = true
            stopWithInvalidateButton.isEnabled = true
            startButton.isEnabled = false
            showReactFragment()
        }

        stopButton.setOnClickListener {
            // TO DO: Implement stop button callback
            println("Stop button clicked!")
            // Show the Placeholder fragment again when Stop is clicked

            stopButton.isEnabled = false
            stopWithInvalidateButton.isEnabled = false
            startButton.isEnabled = true
            showPlaceholderFragment()
        }

        stopWithInvalidateButton.setOnClickListener {
            // TO DO: Implement stop button callback
            println("StopWithInvalidate button clicked!")
            // Show the Placeholder fragment again when Stop is clicked

            stopButton.isEnabled = false
            stopWithInvalidateButton.isEnabled = false
            startButton.isEnabled = true
            showPlaceholderFragment(true)
        }


    }

    private fun showPlaceholderFragment(shouldInvalidate: Boolean = false) {
        // Check if there is any existing fragment in the container
        val existingFragment = parentFragmentManager.findFragmentById(R.id.fragment_container)

        val fragmentTransaction = parentFragmentManager.beginTransaction()

        if (existingFragment == null) {
            println("Adding first Placeholder")
            // No fragment exists, so use add
            fragmentTransaction.add(R.id.fragment_container, Placeholder())
        } else {
            println("Replacing ReactFragment with Placeholder")
            // A fragment exists, so use replace
            fragmentTransaction.replace(R.id.fragment_container, Placeholder())
        }

        fragmentTransaction.commit()

        if(shouldInvalidate) {
            println("shouldInvalidate = true")
            (requireActivity().application as WikipediaApp).tearDownHost()
        }
    }


    private fun showReactFragment() {
        // Check if there is any existing fragment in the container
        val existingFragment = parentFragmentManager.findFragmentById(R.id.fragment_container)

        val fragmentTransaction = parentFragmentManager.beginTransaction()

        if (existingFragment == null) {
            println("Adding first ReactFragment")
            // No fragment exists, so use add
            val reactFragment = ReactFragment.Builder()
                .setComponentName("HelloBrownfield") // Specify the component name for your React Native app
                .build()
            fragmentTransaction.add(R.id.fragment_container, reactFragment)
        } else {
            // A fragment exists, so use replace
            println("Replacing Placeholder with ReactFragment")
            val reactFragment = ReactFragment.Builder()
                .setComponentName("HelloBrownfield") // Specify the component name for your React Native app
                .build()
            fragmentTransaction.replace(R.id.fragment_container, reactFragment)
        }

        fragmentTransaction.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReactNativeStartStop().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
