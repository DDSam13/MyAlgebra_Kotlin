package com.example.myapplication.logic

object Math {

    fun line(a: Double, b: Double): String {
        return if (a == 0.0) {
            if (b == 0.0) "Бесконечно много решений"
            else "Решений нет"
        } else {
            val x = -b / a
            "x = $x"
        }
    }

    fun square(a: Double, b: Double, c: Double): String {
        if (a == 0.0) return "Это не квадратное уравнение"
        val d = b * b - 4 * a * c
        return when {
            d < 0.0 -> "Действительных корней нет"
            d == 0.0 -> {
                val x = -b / (2 * a)
                "Один корень: x = $x"
            }
            else -> {
                val sqrtD = kotlin.math.sqrt(d)
                val x1 = (-b + sqrtD) / (2 * a)
                val x2 = (-b - sqrtD) / (2 * a)
                "Два корня: x₁ = $x1, x₂ = $x2"
            }
        }
    }

    fun nok(a: Int, b: Int): String {
        if (a == 0 || b == 0) return "НОК не определён для нуля"
        val absA = kotlin.math.abs(a)
        val absB = kotlin.math.abs(b)
        val nodValue = nodInt(absA, absB)
        val nok = absA / nodValue * absB
        return "НОК($a, $b) = $nok"
    }

    fun nod(a: Int, b: Int): String {
        if (a == 0 && b == 0) return "НОД не определён для двух нулей"
        val absA = kotlin.math.abs(a)
        val absB = kotlin.math.abs(b)
        val nod = nodInt(absA, absB)
        return "НОД($a, $b) = $nod"
    }

    private fun nodInt(a: Int, b: Int): Int {
        return if (b == 0) a else nodInt(b, a % b)
    }

    fun solveSystem(a1: Double, b1: Double, c1: Double, a2: Double, b2: Double, c2: Double): String {
        val det = a1 * b2 - a2 * b1
        if (det == 0.0) {
            return if ((a1 * c2 - a2 * c1 == 0.0) && (b1 * c2 - b2 * c1 == 0.0)) {
                "Бесконечно много решений"
            } else {
                "Система не имеет решений"
            }
        }
        val x = (c1 * b2 - c2 * b1) / det
        val y = (a1 * c2 - a2 * c1) / det
        return "x = $x\ny = $y"
    }
}