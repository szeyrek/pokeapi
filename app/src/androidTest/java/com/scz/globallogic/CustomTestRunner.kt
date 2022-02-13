package com.scz.globallogic

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication


class CustomTestRunner : AndroidJUnitRunner() {
    /**
     *  Custom Test Runner For Hilt
     */
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}