package ru.alexalekhin.pushapptest

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commit()
        } else {
            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Snackbar.make(
                    main_container,
                    resources.getText(R.string.i_am_sorry_message),
                    Snackbar.LENGTH_LONG
                ).setAction(resources.getText(R.string.accept_sorry), View.OnClickListener {})
                    .show()
            }
        }
    }
}

