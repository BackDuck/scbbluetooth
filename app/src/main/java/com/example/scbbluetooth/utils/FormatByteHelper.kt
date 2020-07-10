package com.example.scbbluetooth.utils

import android.util.Log

object FormatByteHelper {

    private const val LOG_TAG = "Main Activity"

    fun findBeaconPattern(scanRecord: ByteArray?) {
        var startByte = 2
        var patternFound = false
        while (startByte <= 5) {
            if (0xff and (scanRecord?.get(startByte + 2)?.toInt()!!) == 0x02 &&
                (0xff and scanRecord?.get(startByte + 3)?.toInt()) == 0x15
            ) {
                patternFound = true
                break
            }
            startByte++
        }

        if (patternFound) {
            var uuidBytes = ByteArray(16)
            scanRecord?.let { System.arraycopy(it, startByte + 4, uuidBytes, 0, 16) }
            var hexString = bytesToHex(uuidBytes)

            var uuid = hexString.substring(0, 8) + "-" +
                    hexString.substring(8, 12) + "-" +
                    hexString.substring(12, 16) + "-" +
                    hexString.substring(16, 20) + "-" +
                    hexString.substring(20, 32)

            val major =
                (0xff and scanRecord?.get(startByte + 20)?.toInt()!!) * 0x100 + (0xff and scanRecord?.get(
                    startByte + 21
                )?.toInt()!!)
            val minor =
                (0xff and scanRecord[startByte + 22].toInt()) * 0x100 + (0xff and scanRecord[startByte + 23].toInt())
            Log.i(LOG_TAG, "UUID: " + uuid + "\\nmajor: " + major + "\\nminor" + minor)
        }
    }

    private val hexArray: CharArray = "0123456789ABCDEF".toCharArray()
    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (j in bytes.indices) {
            val v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v.ushr(4)]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }
}
