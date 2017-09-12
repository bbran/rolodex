console.log("I am here");

const baseurl = 'http://localhost:12345/api/cards';

function fillInDetails(data) {
	let html= `
		<h1>${data.firstName} ${data.lastName}</h1>
		<h2>${data.companyName}</h2>
		<div>Title: ${data.title}</div>
		`;
	
	for (let phone of data.phoneNumbers) {
		html += `
			<div>
			<b>${phone.phoneNumberType}</b>
			<div>${phone.phoneNumber}</div>
			<form class="delete-phone-form" method="post" action="/api/cards/${data.id}/phones/${phone.id}">
				<button>Delete THIS phone number</button>
			</form>
			</div>
			`;
	}

	html += `
		<form id="create-phone-form" method="post" action="/api/cards/${data.id}/phones">
		<input name="phoneNumberType" id="phoneNumberType" placeholder="what kind of phone?">
		<br>
		<input required name="phoneNumber" id="phoneNumber" placeholder="###-###-####">
		<br>
		<button>Add a phone number</button>
		</form>
		`;
	
	for (let address of data.addresses) {
		html += `
			<div>
			<b>${address.addressType}</b>
			<div>${address.street}</div>
			<div>${address.city}</div>
			<div>${address.state}</div>
			<div>${address.zipCode}</div>
			<form class="delete-address-form" method="post" action="/api/cards/${data.id}/addresses/${address.id}">
			<button>Delete THIS address</button>
			</form>
			</div>


			`;
	}
	html += `
		<form id="create-address-form" method="post" action="/api/cards/${data.id}/addresses">
		<input name="addressType" id="addressType" placeholder="what kind of address?">
		<br>
		<input name="street" id="street" placeholder="what street?">
		<br>
		<input required name="city" id="city" placeholder="what city?">
		<br>
		<input required name="state" id="state" placeholder="what state?">
		<br>
		<input required name="zipCode" id="zipCode" placeholder="what zip?">
		<br>
		<button>Add an address</button>
		</form>
		`;

	$('#card-detail').html(html);
}

function createListElement(card) {
	$('<li></li>')
	.html(`
			<a href="#" data-card-id="${card.id}">
			${card.lastName}, ${card.firstName}
			</a>
			<form class="delete-card-form" method="post" action="/api/cards/${card.id}">
			<button>Delete</button>
			</form>
	`)
	.appendTo($('#card-list'));
}

$(document).on('submit', '#create-phone-form', function (e) {
	e.preventDefault();

	let payload = {
			phoneNumberType: $('#phoneNumberType').val(),
			phoneNumber: $('#phoneNumber').val()
	};

	let ajaxOptions = {
			type: 'POST',
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};

	$.ajax(this.action, ajaxOptions)
	.done(function (data) {
		fillInDetails(data);

	})

	.fail(error => console.error(error));	
});

$(document).on('submit', '#create-address-form', function (e) {
	e.preventDefault();

	let payload = {
			addressType: $('#addressType').val(),
			street: $('#street').val(),
			city: $('#city').val(),
			state: $('#state').val(),
			zipCode: $('#zipCode').val()
	};

	let ajaxOptions = {
			type: 'POST',
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};

	$.ajax(this.action, ajaxOptions)
	.done(function (data) {
		fillInDetails(data);
	})

	.fail(error => console.error(error));	
});

$(document).on('submit', '.delete-card-form', function (e) {
	e.preventDefault();

	$.ajax(this.action, { type: 'DELETE' })
	.done(() => {
		$(this)
		.closest('li')
		.remove();
	})
	.fail(error => console.error(error));
});

$(document).on('submit', '.delete-phone-form', function (e) {
	e.preventDefault();

	$.ajax(this.action, { type: 'DELETE' })
	.done(() => {
		$(this)
		.closest('div')
		.remove();
	})
	.fail(error => console.error(error));
});

$(document).on('submit', '.delete-address-form', function (e) {
	e.preventDefault();

	$.ajax(this.action, { type: 'DELETE' })
	.done(() => {
		$(this)
		.closest('div')
		.remove();
	})
	.fail(error => console.error(error));
});

$('#create-card-form').on('submit', function (e) {
	e.preventDefault();

	let payload = {
			firstName: $('#firstName').val(),
			lastName: $('#lastName').val(),
			title: $('#title').val(),
			companyName: $('#companyName').val(),
			pictureUrl: $('#pictureUrl').val()
	};

	let ajaxOptions = {
			type: 'POST',
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};

	$.ajax(this.action, ajaxOptions)
	.done(function (card) {
		createListElement(card);
	})
//	.fail(function(error)) {
//	console.log(error);

	//above .fail does the same thing.
	.fail(error => console.error(error));	
});


$(document).on('click', 'a[data-card-id]', function (e) {
	e.preventDefault();
	console.log(this);

	const cardId = $(this).data('cardId');

	$.getJSON(baseurl + '/' + cardId, function (data) {
		console.log('Data for', cardId, 'is', data);
		data.companyName = data.companyName || '<i>no company specified</i>';
		fillInDetails(data);
	});

});



$.getJSON( baseurl, function (data) {
	console.log('I got some data', data);
	if (data.length) {
		for (let card of data) {
			createListElement(card);
		}
	} else {
		$('<li></li>')
		.css('color', 'red')
		.html('There is no data')
		.appendTo($('#card-list'));
	}
});