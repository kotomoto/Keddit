package com.koto.keddit

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable


open class RxBaseFragment : Fragment() {
    var subscriptions = CompositeDisposable()
    override fun onResume() {
        super.onResume()

        subscriptions = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()

        subscriptions.clear()
    }
}