$(document).ready(function() {

    var table = $("#table-server").DataTable({
		processing: true,
		serverSide: true,
		responsive: true,
		lengthMenu: [ 10, 15, 20, 25 ],
		ajax: {
			url: "gerenciadorReceitas/listar",
			data: "data"
		},
		columns: [
			{data: 'id'},
			{data: 'nome'},
			{data: 'rendimento'},
			{data: 'tempoPreparo'},
			{data:null,
                render: function(data, type, row, meta) {
                    var ingredientes = '';
                    //loop through all the row details to build output string
                    for (var item in row.ingredientes) {
                        var r = row.ingredientes[item];
                        ingredientes = ingredientes + r.descricao + '</br>';
                    }
                    return ingredientes;
 
                }},
			{data:null,
                render: function(data, type, row, meta) {
                    var modosPreparo = '';
                    //loop through all the row details to build output string
                    for (var item in row.modosPreparo) {
                        var r = row.modosPreparo[item];
                        modosPreparo = modosPreparo + r.etapaPreparo + '</br>';
                    }
                    return modosPreparo;
 
                }},
			{data: 'usuario'},
		],
		dom: 'Bfrtip',
		buttons: [
			{
				text: 'Nova receita',
				attr: {
					id: 'btn-new',
					type: 'button'					
				},
				enabled: true
			},
			{
				text: 'Editar',
				attr: {
					id: 'btn-editar',
					type: 'button'					
				},
				enabled: false
			},
			{
				text: 'Excluir',
				attr: {
					id: 'btn-excluir',
					type: 'button'
				},
				enabled: false
			},
			{
				text: 'Gerenciar',
				attr: {
					id: 'btn-gerenciar',
					type: 'button'
				},
				enabled: false
			}
		]
	});

	// acao do botao de nova receita (abrir modal)
	$("#btn-new").on('click', function() {	
		
		$('#new_nome').val('')
		$('#new_rendimento').val('')
		$('#new_tempoPreparo').val('')

		
		$("span").closest('.error-span').remove();				
		//remover as bordas vermelhas
		$("#new_nome").removeClass("is-invalid");
		$("#new_rendimento").removeClass("is-invalid");
		$("#new_tempoPreparo").removeClass("is-invalid");

		$("#modal-new").modal('show');
	});
	
	// acao do botao de salvar receita
	$("#btn-new-modal").on("click", function(evt) {
		evt.preventDefault();
		var receita = {};
		receita.nome = $("#new_nome").val();
		receita.rendimento = $("#new_rendimento").val();
		receita.tempoPreparo = $("#new_tempoPreparo").val();
		$.ajax({
			method: "POST",
			url: "/receitas/gerenciadorReceitas/salvar",
			data: receita,
			beforeSend: function() {
				// removendo as mensagens
				$("span").closest('.error-span').remove();				
				//remover as bordas vermelhas
				$(".is-invalid").removeClass("is-invalid");
			},
			success: function() {
				$("#modal-new").modal("hide");
				table.button(1).disable();
				table.button(2).disable();
				table.button(3).disable();
				table.ajax.reload();
			},
			statusCode: {
				422: function(xhr) {
					console.log('status error:', xhr.status);
					console.log('responseText:', xhr.responseText);
					
					var errors = $.parseJSON(xhr.responseText);
					console.log('errors:', errors);
					$.each(errors, function(key, val){
						$("#new_" + key).addClass("is-invalid");
						$("#errornew-" + key)
							.addClass("invalid-feedback")
							.append("<span class='error-span'>" + val + "</span>")
					});
				}
			}
		});
	});

	// acao para marcar/desmarcar botoes ao clicar na ordenacao 
	$("#table-server thead").on('click', 'tr', function() {		
		table.button(1).disable();
		table.button(2).disable();
		table.button(3).disable();
	});

	// acao para marcar/desmarcar botoes e seleções ao clicar na pesquisa
	$("#table-server_filter").on('click', function() {		
		table.button(1).disable();
		table.button(2).disable();
		table.button(3).disable();
		$('tr.selected').removeClass('selected');	
	});
	// acao para marcar/desmarcar botoes e seleções ao clicar na navegação
	$("#table-server_paginate").on('click', function() {		
		table.button(1).disable();
		table.button(2).disable();
		table.button(3).disable();
		$('tr.selected').removeClass('selected');	
	});

		// acao para marcar/desmarcar linhas clicadas 
	$("#table-server tbody").on('click', 'tr', function() {		
		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');	
			table.button(1).disable();
			table.button(2).disable();
			table.button(3).disable();
		} else {			
			$('tr.selected').removeClass('selected');			
			$(this).addClass('selected');
			table.buttons().enable();
		}
	});	

	// acao do botao de editar (abrir modal)
	$("#btn-editar").on('click', function() {		
		if ( isSelectedRow() ) {	
			$("#modal-form").modal('show');
			var id = getReceitaId();
			$.ajax({
				method: "GET",
				url: "gerenciadorReceitas/editar/" + id,
				beforeSend: function() {
					// removendo as mensagens de erro
					$("span").closest('.error-span').remove();				
					//remover as bordas vermelhas
					$(".is-invalid").removeClass("is-invalid");
					// abre o modal
					$("#modal-form").modal('show');
				},
				success: function( data ) {
					$("#edt_id").val(data.id);
					$("#edt_nome").val(data.nome);
					$("#edt_rendimento").val(data.rendimento);
					$("#edt_tempoPreparo").val(data.tempoPreparo);
				},
				error: function() {
					alert("Ops... Erro inesperado");
				}
			});
		}
	});

	// submit do formulario para editar
	$("#btn-edit-modal").on("click", function(evt) {
		evt.preventDefault();

		var receita = {};
		receita.id = getReceitaId();
		receita.nome = $("#edt_nome").val();
		receita.rendimento = $("#edt_rendimento").val();
		receita.tempoPreparo = $("#edt_tempoPreparo").val();
		console.log('promo > ', receita);
		$.ajax({
			method: "POST",
			url: "gerenciadorReceitas/editar",
			data: receita,
			beforeSend: function() {
				// removendo as mensagens
				$("span").closest('.error-span').remove();				
				//remover as bordas vermelhas
				$(".is-invalid").removeClass("is-invalid");
			},
			success: function() {
				$("#modal-form").modal("hide");
				table.button(1).disable();
				table.button(2).disable();
				table.button(3).disable();
				table.ajax.reload();
			},
			statusCode: {
				422: function(xhr) {
					console.log('status error:', xhr.status);
					console.log('responseText:', xhr.responseText);
					
					var errors = $.parseJSON(xhr.responseText);
					console.log('errors:', errors);
					$.each(errors, function(key, val){
						$("#edt_" + key).addClass("is-invalid");
						$("#error-" + key)
							.addClass("invalid-feedback")
							.append("<span class='error-span'>" + val + "</span>")
					});
				}
			}
		});
	});
	// acao do botao de excluir (abrir modal)
	$("#btn-excluir").on('click', function() {		
		if ( isSelectedRow() ) {	
			$("#modal-delete").modal('show');
		}
	});

	// exclusao de uma receita
	$("#btn-del-modal").on('click', function() {
		var id = getReceitaId();
		$.ajax({
			method: "GET",
			url: "gerenciadorReceitas/delete/" + id,
			success: function() {
				$("#modal-delete").modal('hide');
				table.button(1).disable();
				table.button(2).disable();
				table.button(3).disable();
				table.ajax.reload();
			},
			error: function() {
				alert("Ops... Exclua primeiros os ingredientes e as etapas de preparo.");
			}
		});
	});

	// acao do botao de gerenciar

	$("#btn-gerenciar").on('click', function() {

		if ( isSelectedRow() ) {	
			var id = getReceitaId();
			window.location.href = "gerenciadorUnitario/" + id;
		}

	});
		
	function getReceitaId() {
		return table.row(table.$('tr.selected')).data().id;
	}
	
	function isSelectedRow() {
		var trow = table.row(table.$('tr.selected'));
		return trow.data() !== undefined;
	}	
});