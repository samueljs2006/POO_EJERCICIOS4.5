class Tiempo(hora: Int = 0, minuto: Int = 0, segundo: Int = 0) {
    var hora: Int
    var minuto: Int
    var segundo: Int

    init {
        val totalSegundos = hora * 3600 + minuto * 60 + segundo
        this.hora = (totalSegundos / 3600) % 24
        this.minuto = (totalSegundos % 3600) / 60
        this.segundo = totalSegundos % 60

        require(hora in 0..23) { "La hora debe estar entre 0 y 23." }
    }

    override fun toString(): String {
        return String.format("%02dh %02dm %02ds", hora, minuto, segundo)
    }

    fun incrementar(t: Tiempo): Boolean {
        val totalSegundos = this.toSegundos() + t.toSegundos()
        return if (totalSegundos >= 86400) {
            false
        } else {
            this.hora = (totalSegundos / 3600) % 24
            this.minuto = (totalSegundos % 3600) / 60
            this.segundo = totalSegundos % 60
            true
        }
    }

    fun decrementar(t: Tiempo): Boolean {
        val totalSegundos = this.toSegundos() - t.toSegundos()
        return if (totalSegundos < 0) {
            false
        } else {
            this.hora = (totalSegundos / 3600) % 24
            this.minuto = (totalSegundos % 3600) / 60
            this.segundo = totalSegundos % 60
            true
        }
    }

    fun comparar(t: Tiempo): Int {
        return when {
            this.toSegundos() < t.toSegundos() -> -1
            this.toSegundos() > t.toSegundos() -> 1
            else -> 0
        }
    }

    fun copiar(): Tiempo {
        return Tiempo(this.hora, this.minuto, this.segundo)
    }

    fun copiarDe(t: Tiempo) {
        this.hora = t.hora
        this.minuto = t.minuto
        this.segundo = t.segundo
    }

    fun sumar(t: Tiempo): Tiempo? {
        val totalSegundos = this.toSegundos() + t.toSegundos()
        return if (totalSegundos >= 86400) null else Tiempo(0, 0, totalSegundos)
    }

    fun restar(t: Tiempo): Tiempo? {
        val totalSegundos = this.toSegundos() - t.toSegundos()
        return if (totalSegundos < 0) null else Tiempo(0, 0, totalSegundos)
    }

    fun esMayorQue(t: Tiempo): Boolean {
        return this.toSegundos() > t.toSegundos()
    }

    fun esMenorQue(t: Tiempo): Boolean {
        return this.toSegundos() < t.toSegundos()
    }

    private fun toSegundos(): Int {
        return hora * 3600 + minuto * 60 + segundo
    }
}

// --------------------- Programa Principal ---------------------

fun solicitarTiempo(): Tiempo {
    print("Ingrese la hora: ")
    val hora = readLine()?.toIntOrNull() ?: 0
    print("Ingrese el minuto (opcional, 0 por defecto): ")
    val minuto = readLine()?.toIntOrNull() ?: 0
    print("Ingrese el segundo (opcional, 0 por defecto): ")
    val segundo = readLine()?.toIntOrNull() ?: 0
    return Tiempo(hora, minuto, segundo)
}

fun main() {
    val t1 = solicitarTiempo()
    println("Tiempo inicial: $t1")

    val t2 = solicitarTiempo()
    println("Tiempo a operar: $t2")

    if (t1.incrementar(t2)) {
        println("Después de incrementar: $t1")
    } else {
        println("Error: Se superan las 23:59:59")
    }

    if (t1.decrementar(t2)) {
        println("Después de decrementar: $t1")
    } else {
        println("Error: Se superan las 00:00:00")
    }

    println("Comparación: ${t1.comparar(t2)}")
    println("Copiar tiempo: ${t1.copiar()}")

    t1.copiarDe(t2)
    println("Después de copiar: $t1")

    val sumado = t1.sumar(t2)
    if (sumado != null) {
        println("Suma de tiempos: $sumado")
    } else {
        println("Error: Resultado mayor que 23:59:59")
    }

    val restado = t1.restar(t2)
    if (restado != null) {
        println("Resta de tiempos: $restado")
    } else {
        println("Error: Resultado menor que 00:00:00")
    }

    println("¿T1 es mayor que T2? ${t1.esMayorQue(t2)}")
    println("¿T1 es menor que T2? ${t1.esMenorQue(t2)}")
}
