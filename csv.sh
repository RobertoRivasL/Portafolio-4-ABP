#!/bin/bash

echo "Generando archivos CSV con datos de prueba..."

# Directorio donde se guardarán los CSV
CSV_DIR="src/test/resources/datos"

# Asegurarse de que el directorio exista
mkdir -p "$CSV_DIR"

# --- Datos para Registro ---

# usuarios_registro_valido.csv
# Columnas: nombreUsuario, correo, contrasena, confirmarContrasena, mensajeEsperado
cat << EOF > "$CSV_DIR/usuarios_registro_valido.csv"
RobertoRivas,roberto.rivas@example.com,MiContrasenaSegura123!,MiContrasenaSegura123!,Registro exitoso
MariaGomez,maria.gomez@test.com,PasswordFuerte_789,PasswordFuerte_789,Registro exitoso
JuanPerez,juan.perez@dominio.org,OtraClave#abc,OtraClave#abc,Registro exitoso
EOF
echo "Creado: $CSV_DIR/usuarios_registro_valido.csv"

# usuarios_registro_invalido.csv
# Columnas: nombreUsuario, correo, contrasena, confirmarContrasena, mensajeErrorEsperado
# Nota: Los mensajes de error esperados deben coincidir con los de la aplicación real.
# Para https://demo.automationtesting.in/Register.html, el comportamiento es más de validación en campo o pop-up, no un mensaje genérico.
# Ajusta estos mensajes según lo que la aplicación muestre realmente.
cat << EOF > "$CSV_DIR/usuarios_registro_invalido.csv"
UsuarioCorto,,pass,pass,El campo de correo es obligatorio
CorreoInvalido,correo.invalido,Password123,Password123,Formato de correo inválido
ContrasenaCorta,test@example.com,abc,abc,Contraseña debe tener al menos 8 caracteres
ContrasenasNoCoinciden,test2@example.com,Pass1234,Pass4321,Las contraseñas no coinciden
EOF
echo "Creado: $CSV_DIR/usuarios_registro_invalido.csv"

# --- Datos para Inicio de Sesión ---

# usuarios_login_valido.csv
# Columnas: usuario, contrasena, mensajeEsperado (o parte de la URL de éxito)
# Para https://practicetestautomation.com/practice-test-login/, el usuario y contraseña son "student" y "Password123"
cat << EOF > "$CSV_DIR/usuarios_login_valido.csv"
student,Password123,logged-in-successfully
EOF
echo "Creado: $CSV_DIR/usuarios_login_valido.csv"

# usuarios_login_invalido.csv
# Columnas: usuario, contrasena, mensajeErrorEsperado
# Para https://practicetestautomation.com/practice-test-login/, el mensaje de error es "Your username is invalid!"
cat << EOF > "$CSV_DIR/usuarios_login_invalido.csv"
usuario_incorrecto,contrasena_incorrecta,Your username is invalid!
student,password_incorrecta,Your username is invalid!
EOF
echo "Creado: $CSV_DIR/usuarios_login_invalido.csv"

echo "Todos los archivos CSV han sido generados."