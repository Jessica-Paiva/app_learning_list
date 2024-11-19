package com.estaciogroup.learninglist.activities
//Modelo de dados
//Dataclass que vai trazer qual tipo de dado será puxado da lista e renderizado no recycleview

data class Topic(val id:Int, val name: String, var isChecked: Boolean, val type: String)

