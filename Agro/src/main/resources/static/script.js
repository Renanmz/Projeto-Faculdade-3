function showTab(tabId) {
  document.querySelectorAll('.tab').forEach(tab => tab.classList.add('hidden'));
  document.getElementById(tabId).classList.remove('hidden');

  if (tabId === 'aba-doenca') {
    carregarDoencas();
  } else if (tabId === 'aba-fazenda') {
    carregarFazendas();
  } else if (tabId === 'aba-ocorrencia') {
    carregarOcorrencias();
    carregarFazendas();
    carregarDoencas();
  } else if (tabId === 'aba-mapa') {
    setTimeout(() => carregarMapa(), 50); // Garante que o div#map já esteja visível
  }
  const logado = sessionStorage.getItem("autenticado") === "true";
  if (logado) {
    const tipo = tabId.replace('aba-', '');
    alternarCadastro(tipo);
  }
}

document.getElementById('form-doenca').addEventListener('submit', async function (e) {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(this));

  const response = await fetch('http://localhost:8080/newdoenca', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
    credentials: 'include'
  });

  if (response.ok) {
    this.reset();
    carregarDoencas();
  } else {
    console.error('Erro ao cadastrar doença:', response.status, await response.text());
  }
});

document.getElementById('form-update-doenca').addEventListener('submit', async function (e) {
  e.preventDefault();
  const id = document.getElementById('update-doenca-id').value;
  const data = Object.fromEntries(new FormData(this));

  const response = await fetch(`http://localhost:8080/updatedoenca/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
    credentials: 'include'
  });

  if (response.ok) {
    this.reset();
    carregarDoencas();
  } else {
    console.error('Erro ao atualizar doença:', response.status, await response.text());
  }
});

async function carregarDoencas() {
  const tabela = document.querySelector('#tabela-doencas tbody');
  const select = document.getElementById('doencaid');
  const updateSelect = document.getElementById('update-doenca-id');

  tabela.innerHTML = '';
  if (select) select.innerHTML = '<option value="">Selecione a Doença</option>';
  if (updateSelect) updateSelect.innerHTML = '<option value="">Selecione o ID</option>';

  const response = await fetch('http://localhost:8080/doencas', { credentials: 'include' });
  if (!response.ok) return;

  const doencas = await response.json();

  doencas.forEach(d => {
    const tr = document.createElement('tr');
    tr.innerHTML = `<td>${d.nome}</td><td>${d.tipo}</td><td>${d.descricao}</td>`;
    tabela.appendChild(tr);

    if (select) {
      const option = document.createElement('option');
      option.value = d.id;
      option.textContent = d.nome;
      select.appendChild(option);
    }
    if (updateSelect) {
      const opt = document.createElement('option');
      opt.value = d.id;
      opt.textContent = `${d.id} - ${d.nome}`;
      updateSelect.appendChild(opt);
    }
  });
}
document.getElementById('buscaDoenca').addEventListener('input', function () {
  const filtro = this.value.toLowerCase();
  const linhas = document.querySelectorAll('#tabela-doencas tbody tr');

  linhas.forEach(linha => {
    const nome = linha.children[0].textContent.toLowerCase();
    linha.style.display = nome.includes(filtro) ? '' : 'none';
  });
});

document.getElementById('form-fazenda').addEventListener('submit', async function (e) {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(this));

  const response = await fetch('http://localhost:8080/newfazenda', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
    credentials: 'include'
  });

  if (response.ok) {
    this.reset();
    carregarFazendas();
  } else {
    console.error('Erro ao cadastrar fazenda:', response.status, await response.text());
  }
});

document.getElementById('form-update-fazenda').addEventListener('submit', async function (e) {
  e.preventDefault();
  const id = document.getElementById('update-fazenda-id').value;
  const data = Object.fromEntries(new FormData(this));

  const response = await fetch(`http://localhost:8080/updatefazenda/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
    credentials: 'include'
  });

  if (response.ok) {
    this.reset();
    carregarFazendas();
  } else {
    console.error('Erro ao atualizar fazenda:', response.status, await response.text());
  }
});

async function carregarFazendas() {
  const tabela = document.querySelector('#tabela-fazendas tbody');
  const select = document.getElementById('fazendaid');
  const updateSelect = document.getElementById('update-fazenda-id');

  tabela.innerHTML = '';
  if (select) select.innerHTML = '<option value="">Selecione a Fazenda</option>';
  if (updateSelect) updateSelect.innerHTML = '<option value="">Selecione o ID</option>';

  const response = await fetch('http://localhost:8080/fazendas', { credentials: 'include' });
  if (!response.ok) return;

  const fazendas = await response.json();

  fazendas.forEach(f => {
    const tr = document.createElement('tr');
    tr.innerHTML = `<td>${f.nomefazenda}</td><td>${f.cidade}</td><td>${f.regiao}</td>`;
    tabela.appendChild(tr);

    const option = document.createElement('option');
    option.value = f.id;
    option.textContent = f.nomefazenda;
    if (select) select.appendChild(option);

    const opt = document.createElement('option');
    opt.value = f.id;
    opt.textContent = `${f.id} - ${f.nomefazenda}`;
    if (updateSelect) updateSelect.appendChild(opt);
  });
}

document.getElementById('form-ocorrencia').addEventListener('submit', async function (e) {
  e.preventDefault();
  const formData = new FormData(this);
  const data = {
    numero: formData.get('numero'),
    estacao: formData.get('estacao'),
    clima: formData.get('clima'),
    descricao: formData.get('descricao'),
    fazendaid: parseInt(formData.get('fazendaid')),
    doencaid: parseInt(formData.get('doencaid'))
  };

  const response = await fetch('http://localhost:8080/newocorrencia', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
    credentials: 'include'
  });

  if (response.ok) {
    this.reset();
    carregarOcorrencias();
  } else {
    console.error('Erro ao cadastrar ocorrência:', response.status, await response.text());
  }
});

document.getElementById('form-update-ocorrencia').addEventListener('submit', async function (e) {
  e.preventDefault();
  const id = document.getElementById('update-ocorrencia-id').value;
  const formData = new FormData(this);
  const data = {
    numero: formData.get('numero'),
    estacao: formData.get('estacao'),
    clima: formData.get('clima'),
    descricao: formData.get('descricao'),
    fazendaid: parseInt(formData.get('fazendaid')),
    doencaid: parseInt(formData.get('doencaid'))
  };

  const response = await fetch(`http://localhost:8080/updateocorrencia/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
    credentials: 'include'
  });

  if (response.ok) {
    this.reset();
    carregarOcorrencias();
  } else {
    console.error('Erro ao atualizar ocorrência:', response.status, await response.text());
  }
});

async function carregarOcorrencias() {
  const tabela = document.querySelector('#tabela-ocorrencias tbody');
  const updateSelect = document.getElementById('update-ocorrencia-id');
  const selectFaz = document.getElementById('update-fazendaid');
  const selectDoe = document.getElementById('update-doencaid');

  tabela.innerHTML = '';
  if (updateSelect) updateSelect.innerHTML = '<option value="">Selecione o ID</option>';
  if (selectFaz) selectFaz.innerHTML = '<option value="">Selecione a Fazenda</option>';
  if (selectDoe) selectDoe.innerHTML = '<option value="">Selecione a Doença</option>';

  const response = await fetch('http://localhost:8080/ocorrencias', { credentials: 'include' });
  if (!response.ok) return;

  const ocorrencias = await response.json();

  ocorrencias.forEach(o => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${o.numero}</td>
      <td>${o.estacao}</td>
      <td>${o.clima}</td>
      <td>${o.descricao}</td>
      <td>${o.fazenda?.nomefazenda}</td>
      <td>${o.doenca?.nome}</td>
    `;
    tabela.appendChild(tr);

    const opt = document.createElement('option');
    opt.value = o.id;
    opt.textContent = `${o.id} - ${o.numero}`;
    updateSelect?.appendChild(opt);
  });

  const fazendas = await fetch('http://localhost:8080/fazendas', { credentials: 'include' }).then(res => res.json());
  fazendas.forEach(f => {
    const opt = document.createElement('option');
    opt.value = f.id;
    opt.textContent = f.nomefazenda;
    selectFaz?.appendChild(opt);
  });

  const doencas = await fetch('http://localhost:8080/doencas', { credentials: 'include' }).then(res => res.json());
  doencas.forEach(d => {
    const opt = document.createElement('option');
    opt.value = d.id;
    opt.textContent = d.nome;
    selectDoe?.appendChild(opt);
  });
}

document.getElementById('buscaOcorrencia').addEventListener('input', function () {
  const filtro = this.value.toLowerCase();
  const linhas = document.querySelectorAll('#tabela-ocorrencias tbody tr');

  linhas.forEach(linha => {
    const numero = linha.children[0].textContent.toLowerCase();
    linha.style.display = numero.includes(filtro) ? '' : 'none';
  });
});

document.getElementById("btn-logout")?.addEventListener("click", () => {
  sessionStorage.removeItem("autenticado");
  location.reload();
});

document.addEventListener('DOMContentLoaded', () => {
  showTab('aba-mapa');
  verificarLogin();
});

document.getElementById("btn-login").addEventListener("click", () => {
  window.location.href = "login.html";
});

function alternarCadastro(tipo) {
  const divCadastro = document.getElementById(`cadastro-${tipo}`);
  const divAtualizar = document.getElementById(`atualizar-${tipo}`);
  const divDeletar = document.getElementById(`deletar-${tipo}`);
  const header = divCadastro.closest('.tab').querySelector('.aba-header h2');

  divCadastro.classList.remove('hidden');
  divAtualizar.classList.add('hidden');
  divDeletar.classList.add('hidden');

  header.textContent = `Cadastro de ${tipo.charAt(0).toUpperCase() + tipo.slice(1)}`;
}

function alternarFormulario(tipo) {
  const divCadastro = document.getElementById(`cadastro-${tipo}`);
  const divAtualizar = document.getElementById(`atualizar-${tipo}`);
  const divDeletar = document.getElementById(`deletar-${tipo}`);
  const header = divCadastro.closest('.tab').querySelector('.aba-header h2');

  // Oculta o formulário de deleção sempre
  divDeletar.classList.add('hidden');

  const cadastroAtivo = !divCadastro.classList.contains('hidden');

  if (cadastroAtivo) {
    divCadastro.classList.add('hidden');
    divAtualizar.classList.remove('hidden');
    header.textContent = `Atualizar ${tipo.charAt(0).toUpperCase() + tipo.slice(1)}`;
  } else {
    divCadastro.classList.remove('hidden');
    divAtualizar.classList.add('hidden');
    header.textContent = `Cadastro de ${tipo.charAt(0).toUpperCase() + tipo.slice(1)}`;
  }
}

function alternarDelecao(tipo) {
  const divCadastro = document.getElementById(`cadastro-${tipo}`);
  const divAtualizar = document.getElementById(`atualizar-${tipo}`);
  const divDeletar = document.getElementById(`deletar-${tipo}`);
  const header = divCadastro.closest('.tab').querySelector('.aba-header h2');

  // Esconde cadastro e atualizar
  divCadastro.classList.add('hidden');
  divAtualizar.classList.add('hidden');

  // Mostra deleção
  divDeletar.classList.remove('hidden');
  carregarIdsParaDelecao(tipo); // atualiza o select com IDs

  // Atualiza título
  header.textContent = `Deletar ${tipo.charAt(0).toUpperCase() + tipo.slice(1)}`;
}

function carregarIdsParaDelecao(tipo) {
  let url = '';
  switch (tipo) {
    case 'doenca':
      url = 'http://localhost:8080/doencas';
      break;
    case 'fazenda':
      url = 'http://localhost:8080/fazendas';
      break;
    case 'ocorrencia':
      url = 'http://localhost:8080/ocorrencias';
      break;
    default:
      console.error('Tipo inválido:', tipo);
      return;
  }

  fetch(url)
    .then(response => response.json())
    .then(data => {
      const select = document.getElementById(`idDeletar${capitalizar(tipo)}`);
      select.innerHTML = '';

      data.forEach(item => {
        const option = document.createElement('option');
        option.value = item.id;
        if (tipo === 'doenca') {
          option.text = `ID ${item.id} - ${item.nome}`;
        } else if (tipo === 'fazenda') {
          option.text = `ID ${item.id} - ${item.nomefazenda}`;
        } else if (tipo === 'ocorrencia') {
          option.text = `ID ${item.id} - Número ${item.numero}`;
        }
        select.appendChild(option);
      });
    })
    .catch(error => console.error('Erro ao carregar IDs:', error));
}

function deletarDoenca() {
  const id = document.getElementById("idDeletarDoenca").value;
  if (confirm(`Tem certeza que deseja deletar a doença com ID ${id}?`)) {
    fetch(`http://localhost:8080/deletedoenca/${id}`, {
      method: 'DELETE',
      credentials: "include"
    })
      .then(response => {
        if (response.ok) {
          alert("Doença deletada com sucesso!");
          carregarIdsParaDelecao("doenca");
          carregarDoencas();
        } else {
          alert("Erro ao deletar doença.");
        }
      })
      .catch(error => console.error('Erro:', error));
  }
}

function deletarFazenda() {
  const id = document.getElementById("idDeletarFazenda").value;
  if (confirm(`Tem certeza que deseja deletar a fazenda com ID ${id}?`)) {
    fetch(`http://localhost:8080/deletefazenda/${id}`, {
      method: 'DELETE',
      credentials: "include"
    })
      .then(response => {
        if (response.ok) {
          alert("Fazenda deletada com sucesso!");
          carregarIdsParaDelecao("fazenda");
          carregarFazendas();
        } else {
          alert("Erro ao deletar fazenda.");
        }
      })
      .catch(error => console.error('Erro:', error));
  }
}

function deletarOcorrencia() {
  const id = document.getElementById("idDeletarOcorrencia").value;
  if (confirm(`Tem certeza que deseja deletar a ocorrência com ID ${id}?`)) {
    fetch(`http://localhost:8080/deleteocorrencia/${id}`, {
      method: 'DELETE',
      credentials: "include"
    })
      .then(response => {
        if (response.ok) {
          alert("Ocorrência deletada com sucesso!");
          carregarIdsParaDelecao("ocorrencia");
          carregarOcorrencias();
        } else {
          alert("Erro ao deletar ocorrência.");
        }
      })
      .catch(error => console.error('Erro:', error));
  }
}

function capitalizar(texto) {
  return texto.charAt(0).toUpperCase() + texto.slice(1);
}

function carregarMapa() {
  if (window._mapaCarregado) return;
  window._mapaCarregado = true;

  const map = L.map('map').setView([-27.5954, -48.5480], 7);

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
  }).addTo(map);

  const focosDoencasSC = [
    {
      cidade: "Xanxerê",
      estado: "SC",
      latitude: -26.875,
      longitude: -52.4042,
      doenca: "Chikungunya",
      casos: 332,
      sintomas: "Febre, dores articulares intensas, exantema.",
      populacaoAfetada: "Adultos e idosos.",
      medidas: "Evitar picadas de mosquito, uso de repelentes.",
      agendamentoDisponivel: false
    },
    {
      cidade: "Florianópolis",
      estado: "SC",
      latitude: -27.5954,
      longitude: -48.5480,
      doenca: "Chikungunya",
      casos: 25,
      sintomas: "Febre, dores articulares intensas, exantema.",
      populacaoAfetada: "Adultos e idosos.",
      medidas: "Evitar picadas de mosquito, uso de repelentes.",
      agendamentoDisponivel: false
    },
    {
      cidade: "Balneário Camboriú",
      estado: "SC",
      latitude: -26.9926,
      longitude: -48.6352,
      doenca: "Dengue",
      casos: 352,
      sintomas: "Febre alta, dores no corpo, manchas vermelhas.",
      populacaoAfetada: "Geral.",
      medidas: "Eliminar criadouros do mosquito Aedes aegypti, uso de repelentes.",
      agendamentoDisponivel: true
    }
  ];

  focosDoencasSC.forEach(foco => {
    const marker = L.marker([foco.latitude, foco.longitude]).addTo(map);
    let popup = `
      <div class="popup-content">
        <h3>${foco.doenca} em ${foco.cidade}/${foco.estado}</h3>
        <p><strong>Casos:</strong> ${foco.casos}</p>
        <p><strong>Sintomas:</strong> ${foco.sintomas}</p>
        <p><strong>População afetada:</strong> ${foco.populacaoAfetada}</p>
        <p><strong>Medidas preventivas:</strong> ${foco.medidas}</p>
    `;
    if (foco.agendamentoDisponivel) {
      popup += `<button onclick="agendarVacina('${foco.cidade}', '${foco.doenca}')">Agendar Vacina</button>`;
    }
    popup += `</div>`;
    marker.bindPopup(popup);
  });
}

function agendarVacina(cidade, doenca) {
  alert(`Redirecionando para agendamento de vacina contra ${doenca} em ${cidade}.`);
}

function verificarAutenticacao() {
  const logado = sessionStorage.getItem("autenticado");
  if (!logado) {
    alert("Você precisa estar logado para acessar essa funcionalidade.");
    window.location.href = "login.html";
    return false;
  }
  return true;
}

function verificarLogin() {
  const isLoggedIn = sessionStorage.getItem("autenticado") === "true";

  // Controla botões e formulários
  document.querySelectorAll('.btn-cadastrar, .btn-atualizar, .btn-deletar').forEach(btn => {
    btn.classList.toggle('hidden', !isLoggedIn);
  });

  document.querySelectorAll('[id^="cadastro-"], [id^="atualizar-"], [id^="deletar-"]').forEach(div => {
    div.classList.toggle('hidden', !isLoggedIn);
  });

  // Oculta título das abas se não estiver logado
  document.querySelectorAll('.aba-header h2').forEach(h2 => {
    const texto = h2.textContent.trim();
    const deveOcultar = texto.startsWith("Cadastro de") || texto.startsWith("Atualizar") || texto.startsWith("Deletar");
    h2.classList.toggle('ocultar-cadastro-h2', !isLoggedIn && deveOcultar);
  });
  if (isLoggedIn) {
    const abas = ["doenca", "fazenda", "ocorrencia"];
    abas.forEach(tipo => {
      const aba = document.getElementById(`aba-${tipo}`);
      if (!aba.classList.contains("hidden")) {
        alternarCadastro(tipo); // mostra cadastro, esconde os outros
      }
    });
  }
}