let data = {}
let provincia 

fetch("https://apis.datos.gob.ar/georef/api/provincias")
.then(respuesta => respuesta.json())
.then(data => mostrarProvincias(data.provincias))
.catch(error => console.log(error))

function mostrarProvincias(data){
    let provincias = document.getElementById("provincia")
    data.map(item => {
        opcion = document.createElement("option")
        opcion.value = item.nombre
        opcion.text = item.nombre
        provincias.appendChild(opcion)
    })
}

function mostrarCiudades(data){
    let ciudades = document.getElementById("ciudad")
    ciudades.innerHTML= ""
    data.map(item => {
        opcion = document.createElement("option")
        opcion.value = item.nombre
        opcion.text = item.nombre
        ciudades.appendChild(opcion)
    })
}

function buscarCiudadesPorPcia(provincia){
    fetch(`https://apis.datos.gob.ar/georef/api/municipios?provincia=${provincia}&campos=id,nombre&max=100`)
    .then(respuesta => respuesta.json())
    .then(data => mostrarCiudades(data.municipios))
    .catch(error => console.log(error))
}

document.querySelector("#provincia").addEventListener("change", (e) => {
    let provincia = (e.target.value).split("-")
    console.log(provincia)

    if(provincia) buscarCiudadesPorPcia(provincia[1])
})

let camposForm = Array.from(document.querySelectorAll(".camposFormulario"))

camposForm.forEach(item => {
    item.addEventListener("change", (e) => {
        data[e.target.name] = e.target.text
    })
})

