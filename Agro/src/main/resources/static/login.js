function mostrarCadastro() {
  document.getElementById('loginSection').style.display = 'none';
  document.getElementById('cadastroSection').style.display = 'block';

  document.querySelector('#loginSection form').reset();
  document.getElementById('cadastroForm').reset();
  document.getElementById('mensagem').textContent = '';
}

function mostrarLogin() {
  document.getElementById('cadastroSection').style.display = 'none';
  document.getElementById('loginSection').style.display = 'block';

  document.getElementById('cadastroForm').reset();
  document.getElementById('mensagem').textContent = '';
  document.querySelector('#loginSection form').reset();
}

async function entrar() {
  const username = document.querySelector('[name="username"]').value;
  const password = document.querySelector('[name="password"]').value;

  try {
    const resp = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ username, password })
    });

    if (resp.ok) {
      const data = await resp.json();
      sessionStorage.setItem("token", data.token);
      sessionStorage.setItem("usuario", username);
      window.location.href = "index.html";
    } else {
      alert("Usuário ou senha inválidos.");
    }
  } catch (error) {
    console.error("Erro ao fazer login:", error);
    alert("Erro ao fazer login. Tente novamente.");
  }
}

window.onload = function () {
  document.getElementById('cadastroForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const form = e.target;
    const dados = {
      username: form.username.value,
      senha: form.password.value
    };

    try {
      const resp = await fetch("http://localhost:8080/usuarios/cadastro", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dados)
      });

      const mensagem = document.getElementById('mensagem');
      if (resp.ok) {
        mensagem.textContent = "Usuário cadastrado com sucesso! Redirecionando para o login...";
        setTimeout(() => mostrarLogin(), 2000);
      } else {
        mensagem.textContent = "Erro ao cadastrar usuário.";
      }
    } catch (error) {
      console.error("Erro ao cadastrar:", error);
    }
  });
};
