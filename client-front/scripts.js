function changeTab(tabIndex) {
    const tabButtons = document.querySelectorAll(".tab-button");
    const tabContents = document.querySelectorAll(".tab-content");

    tabButtons.forEach((button, index) => {
        if (index === tabIndex) {
            if (index === 1) {
                fetchData();
                addTableClickListener(); 
            }
            button.classList.add("active");
            tabContents[index].classList.add("active");
        } else {
            button.classList.remove("active");
            tabContents[index].classList.remove("active");
        }
    });
}


document.getElementById("upload-form").addEventListener("submit", async (event) => {
    event.preventDefault();
    
    const fileInput = document.getElementById('cnab-file');
    const formData = new FormData();
    formData.append('arquivo', fileInput.files[0]); 

    const accessToken = localStorage.getItem('access_token');
    
    try {
        const response = await fetch("http://localhost:8080/cnabs/processamento", { 
            method: "POST",
            headers: {
                'Authorization': 'Bearer ' + accessToken, 
            },
            body: formData,
        });

        if (response.ok) {
            alert("Arquivo enviado com sucesso!");
        } else {
            alert("Erro ao enviar o arquivo!");
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Erro ao enviar o arquivo!");
    }
});



async function fetchData() {
    const accessToken = localStorage.getItem("access_token"); 
    try {
        const response = await fetch("http://localhost:8080/lojas", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + accessToken, 
            },
        });

        if (response.ok) {
            const data = await response.json();
            populateTable(data); 
        } else {
            console.error("Erro na resposta da API");
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
    }
}


function populateTable(data) {
    const tableBody = document.getElementById('data-table').querySelector('tbody');

    tableBody.innerHTML = `<tbody> </tbody>`;
    
    data.forEach(item => {
        const mainRow = document.createElement('tr');
        mainRow.classList.add('collapsible');
        mainRow.classList.add('cursor-pointer');
        const saldoTotalFormato = new Intl.NumberFormat("pt-BR", {
            style: "currency",
            currency: "BRL",
        }).format(item.saldoTotal);
        mainRow.innerHTML = `
            <td >${item.nome}</td>
            <td>${item.proprietario}</td>
            <td>${saldoTotalFormato}</td>
        `;

        const detailsRow = document.createElement('tr');
        detailsRow.style.display = 'none';
        const detailsCell = document.createElement('td');
        detailsCell.colSpan = 4;
        const detailsTable = document.createElement('table');
        detailsTable.classList.add('expanded-table');
        detailsTable.innerHTML = `
            <thead>
                <tr>
                    <th>Tipo</th>
                    <th>Natureza</th>
                    <th>Data Ocorrência</th>
                    <th>Valor</th>
                    <th>CPF</th>
                    <th>Cartão</th>
                </tr>
            </thead>
            <tbody>
                ${item.transacoes.map(transaction => {
                    const valorFormatado = new Intl.NumberFormat("pt-BR", {
                        style: "currency",
                        currency: "BRL",
                    }).format(transaction.valor);

                    const cpfFormatado = formatCPF(transaction.cpf);
                    return `
                    <tr>
                        <td>${transaction.tipo}</td>
                        <td>${transaction.natureza}</td>
                        <td>${transaction.dataOcorrencia}</td>
                        <td>${valorFormatado}</td>
                        <td>${cpfFormatado}</td>
                        <td>${transaction.cartao}</td>
                    </tr>
                `}).join('')}
            </tbody>
        `;
        detailsCell.appendChild(detailsTable);
        detailsRow.appendChild(detailsCell);

        tableBody.appendChild(mainRow);
        tableBody.appendChild(detailsRow);
    });
}



function addTableClickListener() {
    const tableBody = document.getElementById("data-table").querySelector("tbody");
    tableBody.removeEventListener("click", handleTableRowClick);
    tableBody.addEventListener("click", handleTableRowClick);
}

function handleTableRowClick(event) {
    const targetRow = event.target.closest(".collapsible");

    if (targetRow) {
        const detailsRow = targetRow.nextElementSibling;
        detailsRow.style.display = detailsRow.style.display === "none" ? "" : "none";
    }
}

function formatCPF(cpf) {
    if (cpf.length !== 11) {
        return cpf;
    }

    return cpf.slice(0, 3) + '.' + cpf.slice(3, 6) + '.' + cpf.slice(6, 9) + '-' + cpf.slice(9, 11);
}
