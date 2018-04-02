package com.example.ceisutb01.ahogadopaisesdelmundo
import com.github.kittinunf.fuel.android.core.Json
import org.json.JSONException
import org.json.JSONObject
public interface Serializable

//@Serializable
class nation{
    var name=""
    var flag=""
    var capital=""
    var region=""
}
class Pais {
    var nivel1 = ArrayList<nation>()
    var nivel2 = ArrayList<nation>()
    var nivel3 = ArrayList<nation>()
    var nivel4 = ArrayList<nation>()
    var mmm="vainarara"

    private var image: JSONObject = JSONObject()
    private var pais:String= String()

    fun Organizar(info: Json){
        var aux: JSONObject = JSONObject()

        var tamaño=0

        var i1=0
        var i2=1
        var i3=2
        var i4=3
        while(true){
            var aux: JSONObject = JSONObject()
            aux=info.array().getJSONObject(i1)
            try {

                var paisAux= nation()
                paisAux.name=aux.getJSONObject("translations").getString("es")
                if(!paisAux.name.contains(" ") && !paisAux.name.contains("-")) {

                    //aux=info.array().getJSONObject(i2)
                    paisAux.flag = aux.getString("alpha2Code")

                    //aux=info.array().getJSONObject(i3)
                    paisAux.capital = aux.getString("capital")

                    //aux=info.array().getJSONObject(i3)
                    paisAux.region = aux.getString("region")

                    tamaño = paisAux.name.length
                    if (tamaño <= 5) {
                        nivel1.add(paisAux)
                    } else if (tamaño > 5 && tamaño <= 7) {
                        nivel2.add(paisAux)
                    } else if (tamaño > 7 && tamaño <= 9) {
                        nivel3.add(paisAux)
                    } else if (tamaño > 9) {
                        nivel4.add(paisAux)
                    }
                }
            }catch (e: JSONException){
                e.printStackTrace()
            }
            i1+=1
            //i2+=4
            //i3+=4
            //i4+=4
            if(i1>=250){
                break
            }
        }
        i4=0

    }
    fun getName(): String {
        val num=(Math.random() * nivel1.size).toInt()
        return nivel1[num].name
        /*try {
            while(true){

                val num=(Math.random() * 250).toInt()
                image =info.array().getJSONObject(num)
                pais=image.getJSONObject("translations").getString("es")

                if(pais.contains(" ")) {
                    continue
                }else{
                    break
                }
            }
            return pais
            //val ints2 = gson.fromJson("translations", Array<String>::class.java)
            //name=ints2[0].toString()
        } catch (e: JSONException) {
            return "No hay nada"
            e.printStackTrace()
        }*/
    }

    /*private fun getSprites(info: JSONObject): JSONObject? {
        info_Sprites = null
        try {
            info_Sprites = info.getJSONObject("sprites")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return info_Sprites
    }


    fun getImage_Front(info: JSONObject): String? {

        var pkm= Pais()
        image = pkm.getSprites(info)

        try {

            front_default_url = image!!.getString("front_default")


        } catch (e: JSONException) {

            e.printStackTrace()

        }

        return front_default_url
    }
    fun getImage_Back(info: JSONObject): String? {

        var pkm= Pais()
        image = pkm.getSprites(info)

        try {

            back_default_url = image!!.getString("back_default")


        } catch (e: JSONException) {

            e.printStackTrace()

        }

        return back_default_url
    }

*/
}