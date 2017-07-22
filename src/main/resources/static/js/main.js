function save(formData, url, name) {
	saveReloadable(formData, url, name, true);
}

function saveReloadable(formData, url, name, reload) {
	$.ajax({
		url : url,
		type : "POST",
		contentType : 'application/json',
		data : JSON.stringify(formData),
		headers : {
			"X-CSRF-TOKEN" : formData._csrf
		},
		success : function(result) {
			Materialize.toast('<i class="material-icons">done</i>' + name
					+ ' saved.', 2000, 'green rounded');
			if(reload) {
				setTimeout(function() {
					location.reload();
				}, 2500);
			}
		},
		error : function(xhr, resp, text) {
			var errors = $.parseJSON(xhr.responseText).fields;

			if (errors !== undefined) {
				for ( var key in errors) {
					Materialize.toast('<i class="material-icons">error</i>'
							+ key + " " + errors[key], 5000, 'red rounded');
				}
			} else {
				var errorMessage = $.parseJSON(xhr.responseText).message;
				Materialize.toast('<i class="material-icons">error</i>'
						+ errorMessage, 5000, 'red rounded');
			}
		}
	});
}

function remove(id, url, name, csrfToken) {
	$.ajax({
		url : url + id,
		type : "DELETE",
		headers : {
			"X-CSRF-TOKEN" : csrfToken
		},
		success : function(result) {
			Materialize.toast('<i class="material-icons">done</i>' + name
					+ ' deleleted.', 2000, 'green rounded');
			setTimeout(function() {
				location.reload();
			}, 2500);
		},
		error : function(xhr, resp, text) {
			var errorMessage = $.parseJSON(xhr.responseText).message;
			Materialize.toast('<i class="material-icons">error</i>'
					+ errorMessage, 5000, 'red rounded');
		}
	});
}

function fetchContent(url, targetId) {
	$.ajax({
		url : url,
		type : "GET",
		success : function(result) {
			$('#' + targetId).html(result);
		},
		error : function(xhr, resp, text) {
			var errors = $.parseJSON(xhr.responseText).fields;

			if (errors !== undefined) {
				for ( var key in errors) {
					Materialize.toast('<i class="material-icons">error</i>'
							+ key + " " + errors[key], 5000, 'red rounded');
				}
			} else {
				var errorMessage = $.parseJSON(xhr.responseText).message;
				Materialize.toast('<i class="material-icons">error</i>'
						+ errorMessage, 5000, 'red rounded');
			}
		}
	});
}