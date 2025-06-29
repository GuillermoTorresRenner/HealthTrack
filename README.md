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

#### 2. Falta de Testing de Integración

- **Problema:** No se prueba el flujo completo desde el formulario hasta la actualización
- **Solución:** Tests que verifiquen la integración controller-modelo-vista

#### 3. Ausencia de Revisión de Código

- **Problema:** El error es evidente y hubiera sido detectado en una revisión
- **Solución:** Implementar code reviews obligatorios antes de merge

#### 4. Falta de Validación Manual

- **Problema:** No se realizaron pruebas manuales básicas de la funcionalidad
- **Solución:** Checklist de testing manual para funcionalidades críticas

#### 5. Sin Testing de Aceptación

- **Problema:** No se validó que el software cumple con los requisitos del usuario
- **Solución:** Definir criterios de aceptación y casos de prueba específicos

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
