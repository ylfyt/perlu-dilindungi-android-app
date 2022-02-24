package com.example.perlu_dilindungi.models

class FaskesResponseModel{
    var success = false
    var message: String? = null
    var count_total = 0
    var data : ArrayList<FaskesModel>? = null
}

class FaskesModel {
    var id = 0
    var kode : String? = null
    var nama : String? = null
    var kota : String? = null
    var provinsi : String? = null
    var alamat : String? = null
    var latitude : String? = null
    var longitude : String? = null
    var telp : String? = null
    var jenis_faskes : String? = null
    var kelas_rs : String? = null
    var status : String? = null
    var detail : ArrayList<FaskesDetailModel>? = null
    var source_data: String? = null
}

class FaskesDetailModel{
    var id = 0
    var kode : String? = null
    var batch : String? = null
    var divaksin = 0
    var divaksin_1 = 0
    var divaksin_2 = 0
    var batal_vaksin = 0
    var batal_vaksin_1 = 0
    var batal_vaksin_2 = 0
    var pending_vaksin = 0
    var pending_vaksin_1 = 0
    var pending_vaksin_2 = 0
    var tanggal: String?  = null
}