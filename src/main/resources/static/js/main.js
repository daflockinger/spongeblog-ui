	function save(formData,url,name) {
		$.ajax({
			url : url, 
			type : "POST", 
			contentType : 'application/json',
			data : JSON.stringify(formData),
			success : function(result) {
				Materialize.toast('<i class="material-icons">done</i>' + name + ' saved.', 2000,'green rounded');
				setTimeout(function() {location.reload();}, 2500);
			},
			error : function(xhr, resp, text) {
				var errors = $.parseJSON(xhr.responseText).fields;
				
				if(errors !== undefined) {
					for (var key in errors) {
						  Materialize.toast('<i class="material-icons">error</i>' + key + " " + errors[key], 5000,'red rounded');
					}
				} else {
					var errorMessage = $.parseJSON(xhr.responseText).message;
					Materialize.toast('<i class="material-icons">error</i>' + errorMessage, 5000,'red rounded');
				}
			}
		});
	}
	
	function remove(id,url,name){
		$.ajax({
			url : url + id,
			type : "DELETE",
			success : function(result) {
				Materialize.toast('<i class="material-icons">done</i>' + name + ' deleleted.', 2000,'green rounded');
				setTimeout(function() {location.reload();}, 2500);
			},
			error : function(xhr, resp, text) {
				var errorMessage = $.parseJSON(xhr.responseText).message;
				Materialize.toast('<i class="material-icons">error</i>' + errorMessage, 5000,'red rounded');
			}
		});
	}	