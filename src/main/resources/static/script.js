const baseurl = 'http://localhost:12345/api/cards';

function fillInDetails (data)	{
	let html = `
		<h1>${data.firstName} ${data.lastName}</h1>
		<h2>${data.companyName}</h2>
		<div>Title: ${data.title}</div>
	`;
	for (let phone of data.phoneNumbers)	{
		html += `
			<div>
				<b>${phone.phoneNumberType}</b>
				<b>${phone.phoneNumber}</b>
				<form class="delete-phone-form" method="post" action="/api/cards/${data.id}/phones/${phone.id}">
					<button class="btn btn-primary">Delete</button>
				</form>
			</div>
		`;
	}
	for (let address of data.addresses)	{
		html += `
			<div>
				<b>${address.addressType}</b>
				<b>${address.street}</b>
				<b>${address.city}</b>
				<b>${address.state}</b>
				<b>${address.zip}</b>
				<form class="delete-address-form" method="post" action="/api/cards/${data.id}/addresses/${address.id}">
					<button class="btn btn-primary">Delete</button>
				</form>
			</div>
		`;
	}
	html += `
		<br>
		<div class="form-control container-fluid">
			<form id="create-phone-form" method="post" action="/api/cards/${data.id}/phones">
				<div class="row">
					<div class="form-group col">
						<label for="phoneNumberType">Number Type</label>
						<input type="text" name="phoneNumberType" class="form-control" id="phoneNumberType" required></input>
					</div>
					<div class="form-group col">
						<label for="phoneNumber">Phone Number</label>
						<input type="text" name="phoneNumber" class="form-control" id="phoneNumber" required></input>
					</div>
				</div>
				<button type="submit" class="btn btn-primary">Create Phone Number</button>
			</form>
		</div>
		<br>
		<div class="form-control container-fluid">
			<form id="create-address-form" method="post" action="/api/cards/${data.id}/addresses">
				<div class="row">
					<div class="form-group col">
						<label for="addressType">Address Type</label>
						<input type="text" name="addressType" class="form-control" id="addressType" required></input>
					</div>
					<div class="form-group col">
						<label for="street">Street</label>
						<input type="text" name="street" class="form-control" id="street" required></input>
					</div>
				</div>
					<div class="row">
					<div class="form-group col">
						<label for="city">City</label>
						<input type="text" name="city" class="form-control" id="city" required></input>
					</div>
					<div class="form-group col">
						<label for="state">State</label>
						<input type="text" name="state" class="form-control" id="state" required></input>
					</div>
					<div class="form-group col">
						<label for="zipCode">Zip Code</label>
						<input type="text" name="zipCode" class="form-control" id="zipCode" required></input>
					</div>
				</div>
				<button type="submit" class="btn btn-primary">Create Address</button>
			</form>
		</div>
	`
	$('#card-detail').html(html);
}

function createListElement(card)	{
	$('<tr></tr>')
	.html(`
		<td>
			<a href="#" data-card-id="${card.id}">
				${card.firstName} ${card.lastName}
			</a>
		</td>
		<td>
			<form class="delete-card-form" method="post" action="/api/cards/${card.id}">
				<button class="btn btn-primary">Delete</button>
			</form>
		</td>
		`)
	.appendTo($('#card-list'));
}

$(document).on('submit', '.delete-card-form', function (e) {
	e.preventDefault();
	
	$.ajax(this.action, {type: 'DELETE'})
		.done(() => {
			$(this)
				.closest('tr')
				.remove();
		})
		.fail(error => console.error(error));
});

$(document).on('submit', '.delete-phone-form', function (e) {
	e.preventDefault();
	
	$.ajax(this.action, {type: 'DELETE'})
		.done(() => {
			$(this)
				.closest('div')
				.remove();
		})
		.fail(error => console.error(error));
});

$(document).on('submit', '.delete-address-form', function (e) {
	e.preventDefault();
	
	$.ajax(this.action, {type: 'DELETE'})
		.done(() => {
			$(this)
				.closest('div')
				.remove();
		})
		.fail(error => console.error(error));
});

function listAllCards(cards)	{
	if (cards.length)	{
		for (let card of cards)	{
			createListElement(card);
		}
	} else	{
		$('<tr></tr>')
			.css('color', 'red')
			.html('You have no data')
			.appendTo($('#card-list'));
	}
}

$('#create-card-form').on('submit', function (e) {
	e.preventDefault();
	
	let payload = {
		firstName: $('#firstName').val(),
		lastName: $('#lastName').val(),
		title: $('#title').val(),
		companyName: $('#companyName').val(),
		pictureUrl: $('#pictureUrl').val()
	};
	console.log(payload);


	let ajaxOptions = {
		type: 'POST',
		data: JSON.stringify(payload),
		contentType: 'application/json'
	};
	
	$.ajax(this.action, ajaxOptions)
		.done(function (card) {
			createListElement(card);
		})
		.fail(error => console.error(error)
		);
});

$(document).on('submit', '#create-phone-form', function (e) {
	e.preventDefault();
	
	let payload	= {
			phoneNumberType: $('#phoneNumberType').val(),
			phoneNumber: $('#phoneNumber').val()
	};
	
	let ajaxOption = {
			type: 'POST',
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};
	
	$.ajax(this.action, ajaxOption)
		.done(function (card) {
			fillInDetails(card);
		});
});

$(document).on('submit', '#create-address-form', function (e) {
	e.preventDefault();
	
	let payload	= {
			addressType: $('#addressType').val(),
			street: $('#street').val(),
			city: $('#city').val(),
			state: $('#state').val(),
			zipCode: $('#zipCode').val()
	};
	
	let ajaxOption = {
			type: 'POST',
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};
	
	$.ajax(this.action, ajaxOption)
		.done(function (card) {
			fillInDetails(card);
		});
});

$(document).on('click', 'a[data-card-id]', function (e) {
	e.preventDefault();
	const cardId = $(this).data('cardId');
	
	$.getJSON(baseurl + '/' + cardId, function (data)	{
		data.companyName = data.companyName || '<i>No company specified</i>';
		fillInDetails(data);
	});
});

$(searchbutton).on('click', function(e)	{
	e.preventDefault();
	const searchString = $('#searchbox');
	console.log(searchString.val());
	$('#card-list').html(``);
	$.getJSON(baseurl + '?lastName=' + searchString.val(), function (data)	{
		listAllCards(data);
	});

});

$.getJSON(baseurl, function (data)	{
	listAllCards(data);
});