$(document).ready(function() {
	
	var receitaId = window.location.href.split('/').pop();
	
    var tableIngrediente = $("#table-server-ingredientes").DataTable({
		processing: true,
		serverSide: true,
		responsive: true,
		lengthMenu: [ 10, 15, 20, 25 ],
		ajax: {
			url: "gerenciadorUnitario/listarIngredientes/" + receitaId,
			data: "data"
		},
		columns: [
			{data: 'id'},
			{data: 'descricao'},
		],
		dom: 'Bfrtip',
		buttons: [
			{
				text: 'Novo ingrediente',
				attr: {
					id: 'btn-novo-ingrediente',
					type: 'button'					
				},
				enabled: true
			},
			{
				text: 'Editar',
				attr: {
					id: 'btn-editar-ingrediente',
					type: 'button'					
				},
				enabled: false
			},
			{
				text: 'Excluir',
				attr: {
					id: 'btn-excluir-ingrediente',
					type: 'button'
				},
				enabled: false
			},
		]
	});

	// acao do botao de novo ingrediente (abrir modal)
	$("#btn-novo-ingrediente").on('click', function() {	
		
		$('#add_descricao').val('')

		$("span").closest('.error-span').remove();				
		//remover as bordas vermelhas
		$(".is-invalid").removeClass("is-invalid");
		
		$("#modal-ingrediente-add").modal('show');
	});

	// acao do botao de salvar ingrediente (salvar novo ingrediente)
	$("#btn-ingrediente-add-modal").on("click", function(evt) {
		evt.preventDefault();
		var ingrediente = {};
		ingrediente.descricao = $("#add_descricao").val();
		$.ajax({
			method: "POST",
			url: "gerenciadorUnitario/criarIngrediente/" + receitaId,
			data: ingrediente,
			beforeSend: function() {
				// removendo as mensagens
				$("span").closest('.error-span').remove();				
				//remover as bordas vermelhas
				$(".is-invalid").removeClass("is-invalid");
				$("#add_descricao").removeClass("is-invalid");
			},
			success: function() {
				$("#modal-ingrediente-add").modal("hide");
				tableIngrediente.ajax.reload();
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
	$("#table-server-ingredientes thead").on('click', 'tr', function() {		
		tableIngrediente.button(1).disable();
		tableIngrediente.button(2).disable();
	});

	// acao para marcar/desmarcar botoes e seleções ao clicar na pesquisa
	$("#table-server-ingredientes_filter").on('click', function() {		
		tableIngrediente.button(1).disable();
		tableIngrediente.button(2).disable();
		$('tr.selected').removeClass('selected');	
	});
	// acao para marcar/desmarcar botoes e seleções ao clicar na navegação
	$("#table-server-ingredientes_paginate").on('click', function() {		
		tableIngrediente.button(1).disable();
		tableIngrediente.button(2).disable();
		$('tr.selected').removeClass('selected');	
	});

	// acao para marcar/desmarcar linhas clicadas 
	$("#table-server-ingredientes tbody").on('click', 'tr', function() {		
		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');	
			tableIngrediente.button(1).disable();
			tableIngrediente.button(2).disable();
		} else {			
			$('tr.selected').removeClass('selected');			
			$(this).addClass('selected');
			tableIngrediente.button(1).enable();
			tableIngrediente.button(2).enable();
		}
	});	

	// acao do botao de editar ingrediente (abrir modal)
	$("#btn-editar-ingrediente").on('click', function() {		
		if ( isSelectedRow() ) {	
			$("#modal-ingrediente-edt").modal('show');
			var ingredienteId = getIngredienteId();
			$.ajax({
				method: "GET",
				url: "gerenciadorUnitario/editarIngrediente/" + ingredienteId,
				beforeSend: function() {
					// removendo as mensagens de erro
					$("span").closest('.error-span').remove();				
					//remover as bordas vermelhas
					$(".is-invalid").removeClass("is-invalid");
					// abre o modal
					$("#modal-form").modal('show');
				},
				success: function( data ) {
					$("#edt_descricao").val(data.descricao);
				},
				error: function() {
					alert("Ops... Erro inesperado");
				}
			});
		}
	});

	// submit do formulario para editar para ingrediente
	$("#btn-ingrediente-edt-modal").on("click", function(evt) {
		evt.preventDefault();

		var ingrediente = {};
		ingrediente.id = getIngredienteId();
		ingrediente.descricao = $("#edt_descricao").val();
		$.ajax({
			method: "POST",
			url: "gerenciadorUnitario/editarIngrediente",
			data: ingrediente,
			beforeSend: function() {
				// removendo as mensagens
				$("span").closest('.error-span').remove();				
				//remover as bordas vermelhas
				$(".is-invalid").removeClass("is-invalid");
			},
			success: function() {
				$("#modal-ingrediente-edt").modal("hide");
				tableIngrediente.button(1).disable();
				tableIngrediente.button(2).disable();
				tableIngrediente.ajax.reload();
			},
			statusCode: {
				422: function(xhr) {
					console.log('status error:', xhr.status);
					console.log('responseText:', xhr.responseText);
					
					var errors = $.parseJSON(xhr.responseText);
					console.log('errors:', errors);
					$.each(errors, function(key, val){
						$("#edt_" + key).addClass("is-invalid");
						$("#error-ingredienteEdit-" + key)
							.addClass("invalid-feedback")
							.append("<span class='error-span'>" + val + "</span>")
					});
				}
			}
		});
	});

	// acao do botao de excluir ingrediente (abrir modal)
	$("#btn-excluir-ingrediente").on('click', function() {		
		if ( isSelectedRow() ) {	
			$("#modal-ingrediente-del").modal('show');
		}
	});

	// exclusao de um ingrediente
	$("#btn-del-ingrediente-modal").on('click', function() {
		var ingredienteId = getIngredienteId();
		$.ajax({
			method: "GET",
			url: "gerenciadorUnitario/excluirIngrediente/" + ingredienteId,
			success: function() {
				$("#modal-ingrediente-del").modal('hide');
				tableIngrediente.button(1).disable();
				tableIngrediente.button(2).disable();
				tableIngrediente.ajax.reload();
			},
			error: function() {
				alert("Ops... Tente novamente.");
			}
		});
	});


	function getIngredienteId() {
		return tableIngrediente.row(tableIngrediente.$('tr.selected')).data().id;
	}

	function isSelectedRow() {
		var trow = tableIngrediente.row(tableIngrediente.$('tr.selected'));
		return trow.data() !== undefined;
	}	
});