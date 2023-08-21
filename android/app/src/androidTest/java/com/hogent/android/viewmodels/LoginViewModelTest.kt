package com.hogent.android.viewmodels

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hogent.android.data.database.RoomDB
import com.hogent.android.data.repositories.LoginRepository
import com.hogent.android.ui.login.LoginViewModel
import com.hogent.android.util.AuthenticationManager
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginViewModelTest : TestCase() {

    private lateinit var viewModel: LoginViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(
            context,
            RoomDB::class.java
        ).build()
        viewModel = LoginViewModel(LoginRepository(db.customerDao, db.contactDetailsDao))
    }

    @Test
    fun test_loginFails() {
        viewModel.mail.value = "billy@hotmail.com"
        viewModel.pass.value = "mfjlkf"

        viewModel.login()

        val user = AuthenticationManager.getInstance().klant.getOrAwaitValue()

        Assert.assertTrue(user == null)
    }

    @Test
    fun test_loginSuccess() {
        viewModel.mail.value = "billyBillson1997@gmail.com"
        viewModel.pass.value = "Password.1"

        viewModel.login()

        val user = AuthenticationManager.getInstance().klant.getOrAwaitValue()

        Assert.assertTrue(user?.email == "billyBillson1997@gmail.com")
    }
}
