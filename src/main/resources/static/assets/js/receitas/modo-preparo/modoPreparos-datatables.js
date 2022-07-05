$(document).ready(function() {
	
	var receitaId = window.location.href.split('/').pop();
	
    var tableModoPreparo = $("#table-server-modoPreparo").DataTable({
		processing: true,
		serverSide: true,
		responsive: true,
		lengthMenu: [ 10, 15, 20, 25 ],
		ajax: {
			url: "gerenciadorUnitario/listarModosPreparo/" + receitaId,
			data: "data"
		},
		columns: [
			{data: 'id'},
			{data: 'etapaPreparo'},
		],
		dom: 'Bfrtip',
		buttons: [
			{
				text: 'Nova etapa de preparo',
				attr: {
					id: 'btn-novo-modoPreparo',
					type: 'button'					
				},
				enabled: true
			},
			{
				text: 'Editar',
				attr: {
					id: 'btn-editar-modoPreparo',
					type: 'button'					
				},
				enabled: false
			},
			{
				text: 'Excluir',
				attr: {
					id: 'btn-excluir-modoPreparo',
					type: 'button'
				},
				enabled: false
			},
		]
	});

	// acao do botao de novo modo de preparo (abrir modal)
	$("#btn-novo-modoPreparo").on('click', function() {	
		
		$('#add_etapaPreparo').val('')

		$("span").closest('.error-span').remove();				
		//remover as bordas vermelhas
		$(".is-invalid").removeClass("is-invalid");
		
		$("#modal-modoPreparo-add").modal('show');
	});

	$("#btn-modoPreparo-add-modal").on("click", function(evt) {
		evt.preventDefault();
		var modoPreparo = {};
		modoPreparo.etapaPreparo = $("#add_etapaPreparo").val();
		console.log('promo > ', modoPreparo);
		$.ajax({
			method: "POST",
			url: "gerenciadorUnitario/criarModoPreparo/" + receitaId,
			data: modoPreparo,
			beforeSend: function() {
				// removendo as mensagens
				$("span").closest('.error-span').remove();				
				//remover as bordas vermelhas
				$(".is-invalid").removeClass("is-invalid");
				$("#add_etapaPreparo").removeClass("is-invalid");
			},
			success: function() {
				$("#modal-modoPreparo-add").modal("hide");
				tableModoPreparo.ajax.reload();
			},
			statusCode: {
				422: function(xhr) {
					console.log('status error:', xhr.status);
					console.log('responseText:', xhr.responseText);
					
					var errors = $.parseJSON(xhr.responseText);
					console.log('errors:', errors);
					$.each(errors, function(key, val){
						$("#add_" + key).addClass("is-invalid");
						$("#error-" + key)
							.addClass("invalid-feedback")
							.append("<span class='error-span'>" + val + "</span>")
					});
				}
			}
		});
	});

	// acao para marcar/desmarcar botoes ao clicar na ordenacao 
	$("#table-server-modoPreparo thead").on('click', 'tr', function() {		
		tableModoPreparo.button(1).disable();
		tableModoPreparo.button(2).disable();
	});

	// acao para marcar/desmarcar botoes e seleções ao clicar na pesquisa
	$("#table-server-modoPreparo_filter").on('click', function() {		
		tableModoPreparo.button(1).disable();
		tableModoPreparo.button(2).disable();
		$('tr.selected').removeClass('selected');	
	});
	// acao para marcar/desmarcar botoes e seleções ao clicar na navegação
	$("#table-server-modoPreparo_paginate").on('click', function() {		
		tableModoPreparo.button(1).disable();
		tableModoPreparo.button(2).disable();
		$('tr.selected').removeClass('selected');	
	});

	// acao para marcar/desmarcar linhas clicadas 
	$("#table-server-modoPreparo tbody").on('click', 'tr', function() {		
		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');	
			tableModoPreparo.button(1).disable();
			tableModoPreparo.button(2).disable();
		} else {			
			$('tr.selected').removeClass('selected');			
			$(this).addClass('selected');
			tableModoPreparo.button(1).enable();
			tableModoPreparo.button(2).enable();
		}
	});	

	
	// acao do botao de editar modoPreparo (abrir modal)
	$("#btn-editar-modoPreparo").on('click', function() {	
		
		if ( isSelectedRow() ) {	
			$("#modal-modoPreparo-edt").modal('show');
			var modoPreparoId = getModoPreparoId();
			$.ajax({
				method: "GET",
				url: "gerenciadorUnitario/editarModoPreparo/" + modoPreparoId,
				beforeSend: function() {
					// removendo as mensagens de erro
					$("span").closest('.error-span').remove();				
					//remover as bordas vermelhas
					$(".is-invalid").removeClass("is-invalid");
					// abre o modal
					$("#modal-form").modal('show');
				},
				success: function( data ) {
					$("#edt_etapaPreparo").val(data.etapaPreparo);
				},
				error: function() {
					alert("Ops... Erro inesperado");
				}
			});
		}
	});
	
	// submit do formulario para editar para modoPreparo
	$("#btn-modoPreparo-edt-modal").on("click", function(evt) {
		evt.preventDefault();
		var modoPreparo = {};
		modoPreparo.id = getModoPreparoId();
		modoPreparo.etapaPreparo = $("#edt_etapaPreparo").val();
		$.ajax({
			method: "POST",
			url: "gerenciadorUnitario/editarModoPreparo",
			data: modoPreparo,
			beforeSend: function() {
				// removendo as mensagens
				$("span").closest('.error-span').remove();				
				//remover as bordas vermelhas
				$(".is-invalid").removeClass("is-invalid");
			},
			success: function() {
				$("#modal-modoPreparo-edt").modal("hide");
				tableModoPreparo.button(1).disable();
				tableModoPreparo.button(2).disable();
				tableModoPreparo.ajax.reload();
			},
			statusCode: {
				422: function(xhr) {
					console.log('status error:', xhr.status);
					console.log('responseText:', xhr.responseText);
					
					var errors = $.parseJSON(xhr.responseText);
					console.log('errors:', errors);
					$.each(errors, function(key, val){
						$("#edt_" + key).addClass("is-invalid");
						$("#error-modoPreparoEdit-" + key)
							.addClass("invalid-feedback")
							.append("<span class='error-span'>" + val + "</span>")
					});
				}
			}
		});
	});
	
	// acao do botao de excluir modoPreparo (abrir modal)
	$("#btn-excluir-modoPreparo").on('click', function() {		
		if ( isSelectedRow() ) {	
			$("#modal-modoPreparo-del").modal('show');
		}
	});

	// exclusao de um modoPreparo
	$("#btn-del-modoPreparo-modal").on('click', function() {
		var modoPreparoId = getModoPreparoId();
		$.ajax({
			method: "GET",
			url: "gerenciadorUnitario/excluirModoPreparo/" + modoPreparoId,
			success: function() {
				$("#modal-modoPreparo-del").modal('hide');
				tableModoPreparo.button(1).disable();
				tableModoPreparo.button(2).disable();
				tableModoPreparo.ajax.reload();
			},
			error: function() {
				alert("Ops... Tente novamente.");
			}
		});
	});


	function getModoPreparoId() {
		return tableModoPreparo.row(tableModoPreparo.$('tr.selected')).data().id;
	}

	function isSelectedRow() {
		var trow = tableModoPreparo.row(tableModoPreparo.$('tr.selected'));
		return trow.data() !== undefined;
	}	

});