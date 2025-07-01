const token = sessionStorage.getItem("token");

function showTab(tabId) {

  document.querySelectorAll('.tab').forEach(tab => tab.classList.add('hidden'));
  document.getElementById(tabId).classList.remove('hidden');

  document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));
  const activeLink = [...document.querySelectorAll('.nav-link')].find(link =>
    link.getAttribute('onclick')?.includes(tabId)
  );
  if (activeLink) activeLink.classList.add('active');

  if (tabId === 'aba-doenca') {
    carregarDoencas();
  } else if (tabId === 'aba-fazenda') {
    carregarFazendas();
  } else if (tabId === 'aba-ocorrencia') {
    carregarOcorrencias();
    carregarFazendas();
    carregarDoencas();
  } else if (tabId === 'aba-mapa') {
    setTimeout(() => carregarMapa(), 50);
  }
}


document.getElementById('form-doenca').addEventListener('submit', async function (e) {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(this));

  const response = await fetch('http://localhost:8080/newdoenca', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token },
    body: JSON.stringify(data),
  });

  if (response.ok) {
    this.reset();
    carregarDoencas();
  } else {
    console.error('Erro ao cadastrar doença:', response.status, await response.text());
  }
});

async function carregarDoencas() {
  const tabela = document.querySelector('#tabela-doencas tbody');
  const select = document.getElementById('doencaid');
  const updateSelect = document.getElementById('update-doenca-id');

  tabela.innerHTML = '';
  if (select) select.innerHTML = '<option value="">Selecione a Doença</option>';
  if (updateSelect) updateSelect.innerHTML = '<option value="">Selecione o ID</option>';

  const response = await fetch('http://localhost:8080/doencas', {
    headers: { 'Authorization': 'Bearer ' + token }
  });
  if (!response.ok) return;

  const doencas = await response.json();

  doencas.forEach(d => {
    const tr = document.createElement('tr');
    tr.innerHTML = `<td>${d.nome}</td><td>${d.tipo}</td><td>${d.descricao}</td>
      <td><button onclick="editarDoenca('${d.id}')" class="btn btn-info btn-sm text-white"><i class="bi bi-pencil-square me-1"></i>Editar</button>
          <button onclick="deletarDoenca('${d.id}')" class="btn btn-danger btn-sm"><i class="bi bi-trash3 me-1"></i>Deletar</button></td>`;
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
    headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token },
    body: JSON.stringify(data),
  });

  if (response.ok) {
    this.reset();
    carregarFazendas();
  } else {
    console.error('Erro ao cadastrar fazenda:', response.status, await response.text());
  }
});


async function carregarFazendas() {
  const tabela = document.querySelector('#tabela-fazendas tbody');
  const select = document.getElementById('fazendaid');
  const updateSelect = document.getElementById('update-fazenda-id');

  tabela.innerHTML = '';
  if (select) select.innerHTML = '<option value="">Selecione a Fazenda</option>';
  if (updateSelect) updateSelect.innerHTML = '<option value="">Selecione o ID</option>';

  const response = await fetch('http://localhost:8080/fazendas', {
    headers: { 'Authorization': 'Bearer ' + token }
  });
  if (!response.ok) return;

  const fazendas = await response.json();

  fazendas.forEach(f => {
    const tr = document.createElement('tr');
    tr.innerHTML = `<td>${f.nomefazenda}</td><td>${f.cidade}</td><td>${f.regiao}</td>
    <td><button onclick="editarFazenda('${f.id}')" class="btn btn-info btn-sm text-white"><i class="bi bi-pencil-square me-1"></i>Editar</button>
          <button onclick="deletarFazenda('${f.id}')" class="btn btn-danger btn-sm"><i class="bi bi-trash3 me-1"></i>Deletar</button></td>`;
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
    status: formData.get('status'),
    descricao: formData.get('descricao'),
    fazendaid: parseInt(formData.get('fazendaid')),
    doencaid: parseInt(formData.get('doencaid'))
  };

  const response = await fetch('http://localhost:8080/newocorrencia', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token },
    body: JSON.stringify(data),
  });

  if (response.ok) {
    this.reset();
    carregarOcorrencias();
  } else {
    console.error('Erro ao cadastrar ocorrência:', response.status, await response.text());
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

  const response = await fetch('http://localhost:8080/ocorrencias', {
    headers: { 'Authorization': 'Bearer ' + token }
  });
  if (!response.ok) return;

  const ocorrencias = await response.json();

  ocorrencias.forEach(o => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${o.numero}</td>
      <td>${o.estacao}</td>
      <td>${o.clima}</td>
      <td>${o.status}</td>
      <td>${o.descricao}</td>
      <td>${o.fazenda?.nomefazenda}</td>
      <td>${o.doenca?.nome}</td>
      <td>
          <button onclick="editarOcorrencia('${o.id}')" class="btn btn-info btn-sm text-white"><i class="bi bi-pencil-square me-1"></i>Editar</button>
          <button onclick="deletarOcorrencia('${o.id}')" class="btn btn-danger btn-sm"><i class="bi bi-trash3 me-1"></i>Deletar</button>
        </td>
    `;
    tabela.appendChild(tr);

    const opt = document.createElement('option');
    opt.value = o.id;
    opt.textContent = `${o.id} - ${o.numero}`;
    updateSelect?.appendChild(opt);
  });

  const fazendas = await fetch('http://localhost:8080/fazendas', { headers: { 'Authorization': 'Bearer ' + token } }).then(res => res.json());
  fazendas.forEach(f => {
    const opt = document.createElement('option');
    opt.value = f.id;
    opt.textContent = f.nomefazenda;
    selectFaz?.appendChild(opt);
  });

  const doencas = await fetch('http://localhost:8080/doencas', { headers: { 'Authorization': 'Bearer ' + token } }).then(res => res.json());
  doencas.forEach(d => {
    const opt = document.createElement('option');
    opt.value = d.id;
    opt.textContent = d.nome;
    selectDoe?.appendChild(opt);
  });
}

document.getElementById('buscaOcorrencia').addEventListener('input', function () {
  const filtro = this.value.toLowerCase();
  const campoSelecionado = document.getElementById('filtroOcorrenciaCampo').value;
  const linhas = document.querySelectorAll('#tabela-ocorrencias tbody tr');

  const indices = {
  numero: 0,
  estacao: 1,
  clima: 2,
  descricao: 3,
  fazenda: 4, 
  doenca: 5    
};

  const indice = indices[campoSelecionado] ?? 0;

  linhas.forEach(linha => {
    const texto = linha.children[indice]?.textContent.toLowerCase() || '';
    linha.style.display = texto.includes(filtro) ? '' : 'none';
  });

});
document.getElementById('filtroOcorrenciaCampo').addEventListener('change', function () {
  const campoBusca = document.getElementById('buscaOcorrencia');
  campoBusca.value = ''; // Limpa o input
  campoBusca.dispatchEvent(new Event('input')); // Dispara evento para atualizar a tabela
});

document.getElementById("btn-logout")?.addEventListener("click", () => {
  sessionStorage.removeItem("autenticado");
  location.reload();
});

document.addEventListener('DOMContentLoaded', () => {
  showTab('aba-ocorrencia');
});

document.getElementById("btn-logout").addEventListener("click", () => {
  window.location.href = "login.html";
});

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

const modalDoenca = new bootstrap.Modal(document.getElementById('modal-doenca'));
const modalFazenda = new bootstrap.Modal(document.getElementById('modal-fazenda'));
const modalOcorrencia = new bootstrap.Modal(document.getElementById('modal-ocorrencia'));


async function editarOcorrencia(id) {
  try {
    const [ocorrenciaResp, fazendasResp, doencasResp] = await Promise.all([
      fetch(`http://localhost:8080/ocorrencia/${id}`, { headers: { 'Authorization': 'Bearer ' + token } }),
      fetch("http://localhost:8080/fazendas", { headers: { 'Authorization': 'Bearer ' + token } }),
      fetch("http://localhost:8080/doencas", { headers: { 'Authorization': 'Bearer ' + token } })
    ]);

    if (!ocorrenciaResp.ok) throw new Error("Ocorrência não encontrada");

    const ocorrencia = await ocorrenciaResp.json();
    const fazendas = await fazendasResp.json();
    const doencas = await doencasResp.json();

    document.getElementById('modal-ocorrencia-id').value = ocorrencia.id;
    document.getElementById('modal-ocorrencia-numero').value = ocorrencia.numero;
    document.getElementById('modal-ocorrencia-estacao').value = ocorrencia.estacao;
    document.getElementById('modal-ocorrencia-clima').value = ocorrencia.clima;
    document.getElementById('modal-ocorrencia-status').value = ocorrencia.status;
    document.getElementById('modal-ocorrencia-descricao').value = ocorrencia.descricao;
    document.getElementById('modal-ocorrencia-data').value = ocorrencia.data;

    const fazendaSelect = document.getElementById('modal-ocorrencia-fazenda');
    fazendaSelect.innerHTML = '<option value="">Selecione a Fazenda</option>';
    fazendas.forEach(f => {
      const opt = document.createElement('option');
      opt.value = f.id;
      opt.textContent = f.nomefazenda;
      if (f.id === ocorrencia.fazendaid || f.id === ocorrencia.fazenda?.id) opt.selected = true;
      fazendaSelect.appendChild(opt);
    });

    const doencaSelect = document.getElementById('modal-ocorrencia-doenca');
    doencaSelect.innerHTML = '<option value="">Selecione a Doença</option>';
    doencas.forEach(d => {
      const opt = document.createElement('option');
      opt.value = d.id;
      opt.textContent = d.nome;
      if (d.id === ocorrencia.doencaid || d.id === ocorrencia.doenca?.id) opt.selected = true;
      doencaSelect.appendChild(opt);
    });
      modalOcorrencia.show();
  } catch (error) {
    alert("Erro ao carregar ocorrência: " + error.message);
  }
}


function salvarEdicaoOcorrencia() {
  const id = document.getElementById('modal-ocorrencia-id').value;
  const data = {
    numero: document.getElementById('modal-ocorrencia-numero').value,
    estacao: document.getElementById('modal-ocorrencia-estacao').value,
    clima: document.getElementById('modal-ocorrencia-clima').value,
    status: document.getElementById('modal-ocorrencia-status').value,
    descricao: document.getElementById('modal-ocorrencia-descricao').value,
    fazendaid: document.getElementById('modal-ocorrencia-fazenda').value,
    doencaid: document.getElementById('modal-ocorrencia-doenca').value,
    data: document.getElementById('modal-ocorrencia-data').value
  };

  fetch(`http://localhost:8080/updateocorrencia/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json", 'Authorization': 'Bearer ' + token },
    body: JSON.stringify(data),
  })
    .then(response => {
      if (!response.ok) throw new Error("Falha ao editar");
      alert("Ocorrência atualizada com sucesso!");
      modalOcorrencia.hide();
      carregarOcorrencias();
    })
    .catch(error => {
      alert("Erro ao atualizar: " + error.message);
    });
}


function editarDoenca(id) {
  fetch(`http://localhost:8080/doenca/${id}`, {
    headers: { 'Authorization': 'Bearer ' + token }
  })
    .then(response => {
      if (!response.ok) throw new Error("Não encontrado");
      return response.json();
    })
    .then(data => {
      document.getElementById('modal-doenca-id').value = data.id;
      document.getElementById('modal-doenca-nome').value = data.nome;
      document.getElementById('modal-doenca-descricao').value = data.descricao;
      if (data.tipo === "Doença") {
        document.getElementById('modal-tipo-doenca').checked = true;
      } else if (data.tipo === "Praga") {
        document.getElementById('modal-tipo-praga').checked = true;
      }
      modalDoenca.show();
    })
    .catch(error => {
      alert("Erro ao carregar doença: " + error.message);
    });
}

function salvarEdicaoDoenca() {
  const id = document.getElementById('modal-doenca-id').value;
  const data = {
    nome: document.getElementById('modal-doenca-nome').value,
    tipo: document.querySelector('input[name="modal-tipo"]:checked').value,
    descricao: document.getElementById('modal-doenca-descricao').value
  };
  fetch(`http://localhost:8080/updatedoenca/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json", 'Authorization': 'Bearer ' + token },
    body: JSON.stringify(data),
  })
    .then(response => {
      if (!response.ok) throw new Error("Falha ao editar");
      alert("Doenca atualizada com sucesso!");
      modalDoenca.hide();
      carregarDoencas();
    })
    .catch(error => {
      alert("Erro ao atualizar: " + error.message);
    });
}

function editarFazenda(id) {
  fetch(`http://localhost:8080/fazenda/${id}`, { headers: { 'Authorization': 'Bearer ' + token } })
    .then(response => {
      if (!response.ok) throw new Error("Não encontrado");
      return response.json();
    })
    .then(data => {
      document.getElementById('modal-fazenda-id').value = data.id;
      document.getElementById('modal-fazenda-nome').value = data.nomefazenda;
      document.getElementById('modal-fazenda-cidade').value = data.cidade;
      document.getElementById('modal-fazenda-regiao').value = data.regiao;
      modalFazenda.show();
    })
    .catch(error => {
      alert("Erro ao carregar fazenda: " + error.message);
    });
}

function salvarEdicaoFazenda() {
  const id = document.getElementById('modal-fazenda-id').value;
  const data = {
    nome: document.getElementById('modal-fazenda-nome').value,
    cidade: document.getElementById('modal-fazenda-cidade').value,
    hectares: document.getElementById('modal-fazenda-regiao').value
  };
  fetch(`http://localhost:8080/updatefazenda/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json", 'Authorization': 'Bearer ' + token },
    body: JSON.stringify(data),
  })
    .then(response => {
      if (!response.ok) throw new Error("Falha ao editar");
      alert("Fazenda atualizada com sucesso!");
      modalFazenda.hide();
      carregarFazendas();
    })
    .catch(error => {
      alert("Erro ao atualizar: " + error.message);
    });
}

async function deletarDoenca(id) {
  const ocorrencias = await fetch("http://localhost:8080/ocorrencias", { headers: { 'Authorization': 'Bearer ' + token } }).then(res => res.json());
  const relacionadas = ocorrencias.some(ocorrencia => ocorrencia.doenca.id === id);

  if (relacionadas.length > 0) {
    if (!confirm("Esta doença está associada a ocorrências. Deseja realmente deletá-la? Todas as ocorrências relacionadas serão removidas.")) return;
  } else {
    if (!confirm("Tem certeza que deseja deletar esta doença?")) return;
  }
  await fetch(`http://localhost:8080/deletedoenca/${id}`, {
    method: "DELETE",
    headers: { 'Authorization': 'Bearer ' + token }
  })
    .then(() => {
      carregarDoencas();
      carregarOcorrencias();
    });
}


async function deletarFazenda(id) {
  const ocorrencias = await fetch("http://localhost:8080/ocorrencias", {headers: { 'Authorization': 'Bearer ' + token }}).then(res => res.json());
  const relacionadas = ocorrencias.some(ocorrencia => ocorrencia.fazenda.id === id);

  if (relacionadas.length > 0) {
    if (!confirm("Esta fazenda está associada a ocorrências. Deseja realmente deletá-la? Todas as ocorrências relacionadas serão removidas.")) return;
  } else {
    if (!confirm("Tem certeza que deseja deletar esta fazenda?")) return;
  }
  await fetch(`http://localhost:8080/deletefazenda/${id}`, {
    method: "DELETE",
    headers: { 'Authorization': 'Bearer ' + token }
  })
    .then(() => {
      carregarFazendas();
      carregarOcorrencias();
    });
}


function deletarOcorrencia(id) {
  if (!confirm("Tem certeza que deseja deletar esta Ocorrência?")) return;
  fetch(`http://localhost:8080/deleteocorrencia/${id}`, {
    method: "DELETE",
    headers: { 'Authorization': 'Bearer ' + token }
  })
    .then(res => {
      if (!res.ok) throw new Error("Erro ao deletar");
      alert("Ocorrência deletada com sucesso.");
      carregarOcorrencias();
    })
    .catch(err => alert("Erro ao deletar ocorrência: " + err.message));
}

function abrirRelatorio() {
  fetch('http://localhost:8080/ocorrencias', {
    headers: { 'Authorization': 'Bearer ' + token }
  })
    .then(response => {
      if (!response.ok) throw new Error("Erro ao buscar ocorrências");
      return response.json();
    })
    .then(data => {
      const corpoTabela = document.getElementById('tabela-relatorio-corpo');
      corpoTabela.innerHTML = '';

      // Ordenar por data decrescente (mais recente primeiro)
      data.sort((a, b) => new Date(b.data) - new Date(a.data));

      data.forEach(ocorrencia => {
        const linha = document.createElement('tr');
        linha.innerHTML = `
          <td>${new Date(ocorrencia.data).toLocaleDateString()}</td>
          <td>${ocorrencia.doenca?.nome || ''}</td>
          <td>${ocorrencia.fazenda?.nomefazenda || ''}</td>
          <td>${ocorrencia.descricao || ''}</td>
        `;
        corpoTabela.appendChild(linha);
      });

      // Abrir modal
      const modal = new bootstrap.Modal(document.getElementById('modal-relatorio'));
      modal.show();
    })
    .catch(error => {
      alert("Erro ao carregar relatório: " + error.message);
    });
}

