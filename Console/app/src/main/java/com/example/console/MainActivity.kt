package com.example.console

class DataItem(var key: String, initValue: String?) {
    private var _value: String? = initValue

    var value: String?
        get() = _value?.let { "[Value] $it" } ?: "[NULL]"
        set(newValue) {
            _value = newValue?.trim()?.takeIf { it.isNotEmpty() }
        }
}

data class DataSnapshot(val key: String, val value: String?)

val mutableDatabase = ArrayList<DataItem>()

fun main() {
    println("PROGRAM CRUD CONSOLE")

    while (true) {
        println("\n[MENU ITEM]")
        println("1. Tambah Data")
        println("2. List Data")
        println("3. Edit Data")
        println("4. Haput Data")
        println("5. Show Data")
        println("0. Keluar")
        println("Pilih Menu (0-5): ")

        val choice = readlnOrNull()?.toIntOrNull()

        when (choice) {
            1 -> addData()
            2 -> listData()
            3 -> editData()
            4 -> deleteData()
            5 -> showData()
            0 -> { println("Program selesai."); return }
            else -> println("Input tidak valid. Masukkan angka 0-5.")
        }
    }
}

fun addData() {
    println("Masukkan Key: ")
    val keyInput = readlnOrNull()?.takeIf { it.isNotEmpty() }

    if (keyInput == null) {
        println("Key tidak boleh kosong!")
        return
    }

    if (mutableDatabase.any { it.key == keyInput }) {
        println("Key '$keyInput' sudah terdaftar.")
        return
    }

    println("Masukkan Value: ")
    val valInput = readlnOrNull()

    mutableDatabase.add(DataItem(keyInput, valInput))
    println("Data berhasil ditambahkan.")
}

fun listData() {
    if (mutableDatabase.isEmpty()) {
        println("Database kosong.")
        return
    }

    val immutableView: List<DataSnapshot> = mutableDatabase.map {
        DataSnapshot(it.key, it.value)
    }

    println("\nDAFTAR DATA")
    immutableView.forEachIndexed { index, item ->
        println("${index + 1}. Key: ${item.key.padEnd(10)} | Value: ${item.value}")
    }
    println("Total: ${immutableView.size} item")
}

fun editData() {
    print("Masukkan Key yang ingin di-edit: ")
    val searchKey = readlnOrNull()?.trim() ?: return

    val index = mutableDatabase.indexOfFirst { it.key == searchKey }
    if (index == -1) {
        println("Key tidak ditemukan.")
        return
    }

    println("Data saat ini: ${mutableDatabase[index].key} -> ${mutableDatabase[index].value}")
    print("Masukkan Value baru: ")
    val newVal = readlnOrNull()

    mutableDatabase[index].value = newVal
    println("Data berhasil di-update.")
}

fun deleteData() {
    print("Masukkan Key yang ingin dihapus: ")
    val searchKey = readlnOrNull()?.trim() ?: return

    val index = mutableDatabase.indexOfFirst {it.key == searchKey}
    if (index == -1) {
        println("Key tidak ditemukan.")
        return
    }

    mutableDatabase.removeAt(index)
    println("Data dengan key '$searchKey' berhasil dihapus.")
}

fun showData() {
    print("Masukkan Key untuk ditampilkan: ")
    val searchKey = readlnOrNull()?.trim() ?: return

    val found = mutableDatabase.find {it.key == searchKey}

    found?.let { item ->
        println("\nDETAIL DATA")
        println("Key: ${item.key}")
        val safeValue = item.value ?: "[TIDAK ADA VALUE]"
        println("Value: $safeValue")
    } ?: run {
        println("Data dengan key '$searchKey' tidak ditemukan.")
    }
}