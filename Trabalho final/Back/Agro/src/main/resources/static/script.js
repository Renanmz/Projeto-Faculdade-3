function carregarDoencas() {
  fetch("http://localhost:8080/doencas", {
    credentials: "include",
  })
    .then((res) => {
      if (!res.ok) throw new Error("Erro ao buscar doenças");
      return res.json();
    })
    .then((data) => {
      const container = document.getElementById("doencas");
      container.innerHTML = "<h3>Doenças</h3><ul>" + data.map(d => `<li>${d.nome}</li>`).join("") + "</ul>";
    })
    .catch((err) => {
      console.error("Erro ao carregar doenças:", err);
    });
}

function carregarFazendas() {
  fetch("http://localhost:8080/fazendas", {
    credentials: "include",
  })
    .then((res) => {
      if (!res.ok) throw new Error("Erro ao buscar fazendas");
      return res.json();
    })
    .then((data) => {
      const container = document.getElementById("fazendas");
      container.innerHTML = "<h3>Fazendas</h3><ul>" + data.map(f => `<li>${f.nome}</li>`).join("") + "</ul>";
    })
    .catch((err) => {
      console.error("Erro ao carregar fazendas:", err);
    });
}

function carregarOcorrencias() {
  fetch("http://localhost:8080/ocorrencias", {
    credentials: "include",
  })
    .then((res) => {
      if (!res.ok) throw new Error("Erro ao buscar ocorrências");
      return res.json();
    })
    .then((data) => {
      const container = document.getElementById("ocorrencias");
      container.innerHTML = "<h3>Ocorrências</h3><ul>" + data.map(o => `<li>${o.descricao}</li>`).join("") + "</ul>";
    })
    .catch((err) => {
      console.error("Erro ao carregar ocorrências:", err);
    });
}

// Carrega tudo ao abrir a página
window.onload = () => {
  carregarDoencas();
  carregarFazendas();
  carregarOcorrencias();
};
