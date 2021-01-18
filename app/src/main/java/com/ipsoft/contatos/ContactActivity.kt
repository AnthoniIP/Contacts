package com.ipsoft.contatos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
    }

    override fun onResume() {
        super.onResume()
        if (!hasPermission(Manifest.permission.READ_CONTACTS) ||
            !hasPermission(Manifest.permission.WRITE_CONTACTS)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.READ_CONTACTS
                ), RC_PERMISSION_CONTACT
            )
        } else {
            init()
        }
    }

    private fun hasPermission(permission: String): Boolean {

        return ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == RC_PERMISSION_CONTACT && grantResults.isNotEmpty()) {
            if (!grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, R.string.error_permission, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun init() {
        if (supportFragmentManager.findFragmentByTag(TAG_CONTACT_LIST) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ContactListFragment(), TAG_CONTACT_LIST)
                .commit()
        }
    }

    companion object {
        private const val TAG_CONTACT_LIST = "contact_fragment"
        private const val RC_PERMISSION_CONTACT = 1
    }
}