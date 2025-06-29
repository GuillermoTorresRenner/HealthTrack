# HealthTrack - Aplicación de Seguimiento de Peso

## Descripción

Aplicación web desarrollada en Spring Boot para el seguimiento del peso de usuarios, diseñada específicamente para demostrar la importancia del testing en el desarrollo de software.

## Análisis del Error Detectado

### Descripción del Error en la Lógica del Código

El error se encuentra en el método `actualizarPeso()` de la clase `Users` ubicada en `src/main/java/com/devateleur/healthtrack/models/Users.java`.

**Comportamiento esperado:**

```java
public void actualizarPeso(double nuevoPeso) {
    this.peso = nuevoPeso; // Asignar el nuevo peso directamente
}
```

**Comportamiento actual (erróneo):**

```java
public void actualizarPeso(double nuevoPeso) {
    this.peso -= 1; // ERROR: Resta 1 kg al peso actual, ignora el parámetro
}
```

El método ignora completamente el parámetro `nuevoPeso` y en su lugar resta 1 kilogramo al peso actual, lo que resulta en un comportamiento completamente incorrecto.

### Impacto del Error en la Experiencia del Usuario

Este error tiene consecuencias graves en la experiencia del usuario:

1. **Pérdida de confianza en la aplicación:**

   - Los usuarios ingresan un peso específico (ej: 75 kg) pero ven un valor diferente (74 kg)
   - La aplicación muestra datos incorrectos de manera sistemática

2. **Funcionalidad principal comprometida:**

   - La función core de la aplicación (registro de peso) no funciona correctamente
   - Los usuarios no pueden confiar en los datos almacenados

3. **Confusión y frustración:**

   - Los usuarios pueden pensar que hay un problema con su entrada de datos
   - Genera desconfianza hacia el sistema completo

4. **Datos históricos incorrectos:**
   - Cada actualización corrompe los datos del usuario
   - El tracking de progreso se vuelve inútil

### Falta de Procesos de Validación y Pruebas en el Desarrollo Actual

La presencia de este error evidencia las siguientes deficiencias en el proceso de desarrollo:

#### 1. Ausencia de Testing Unitario

- **Problema:** No existen pruebas que validen el comportamiento del método `actualizarPeso()`
- **Solución:** Implementar tests unitarios que verifiquen:
  ```java
  @Test
  void testActualizarPeso() {
      Users usuario = new Users("Test", 70.0);
      usuario.actualizarPeso(75.0);
      assertEquals(75.0, usuario.getPeso());
  }
  ```

#### 2. Falta de Testing de Humo

- **Problema:** No se prueba que el sistema pueda ser simplemente levantado.
- **Solución:** Implementar tests de humo que verifiquen el levantamiento de la aplicación
  aplicación

#### 3. Falta de Testing de Integración

- **Problema:** No se prueba el flujo completo desde el formulario hasta la actualización de los datos ingresados, por lo cual pudieran no detectarse tanto errores en la lógica como en los componentes visuales de la aplicación.
- **Solución:** Tests que verifiquen la integración controller-modelo-vista

#### 4. Ausencia de Revisión de la calidad del código

- **Problema:** Con la sintegración de SonarCloud, no se revisa la calidad del código ni se detectan problemas potenciales
- **Solución:** Integrar SonarCloud en el pipeline de CI/CD para análisis de calidad.

#### 5. Sin Testing de Aceptación

- **Problema:** No se validó que el software cumple con los requisitos del usuario
- **Solución:** Definir criterios de aceptación y casos de prueba específicos

#### 5. Sin pruebas de regresión

- **Problema:** No se realizan pruebas de regresión para asegurar que los cambios no rompan funcionalidades existentes
- **Solución:** Implementar un conjunto de pruebas de regresión que se ejecuten en cada despliegue.

#### 5. Falta de testing de rendimiento

- **Problema:** No se verifica que la aplicación pueda manejar múltiples usuarios y cargas de datos
- **Solución:** Implementar pruebas de carga y estrés para evaluar el rendimiento bajo condiciones reales.

## Lecciones Aprendidas

Este ejercicio demuestra que:

- **Un error simple puede comprometer completamente la funcionalidad**
- **El testing automatizado habría detectado este error inmediatamente**
- **La confianza del usuario se pierde rápidamente con comportamientos incorrectos**
- **Los procesos de QA son esenciales, no opcionales**

## Recomendaciones

1. Implementar TDD (Test-Driven Development)
2. Establecer cobertura mínima de testing
3. Automatizar pruebas en el pipeline CI/CD
4. Realizar testing manual sistemático
5. Implementar revisiones de código obligatorias

# HealthTrack
