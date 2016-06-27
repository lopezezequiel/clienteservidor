var app = document.getElementById('app');

/*
 * INDEX
 */
Bappse.route(/^$/, function() {
	$views.index.render(app);
});

/*
 * CRUD Category
 */
Bappse.route(/^categories(?:\/page([0-9]+))?$/, function(page) {
	page = parseInt(page || 0);

	Bappse.AjaxQueue(
			$dao.category.findAll({
				offset: page * $config.pagination.itemsByPage,
				limit: $config.pagination.itemsByPage
			}),
			$dao.category.count()
	).success(function(categories, length) {

		$views.categories.render(
			app,{
				categories: categories,
				pagination: $service.pagination(
					page,
					Math.ceil(length/$config.pagination.itemsByPage),
					$config.pagination.size
				)
			}
		);
	}).ok();
});

Bappse.route(/^categories\/(\d+)$/, function(id) {
	Bappse.AjaxQueue(
		$dao.category.findById(parseInt(id)),
		$dao.category.findAll()
	).success(function(category, categories) {
		$views.category.render(
			app, {
				category: category,
				categories: categories
			}
		);
	}).ok();
});

Bappse.route(/^categories\/new$/, function() {
	$dao.category.findAll().success(function(categories) {
		$views.category.render(
			app, {
				category: {},
				categories: categories
			}
		);
	}).ok();
});

/*
 * CRUD Province
 */
Bappse.route(/^provinces$/, function() {
	
	$dao.province.findAll().success(function(provinces) {
		$views.provinces.render(
			app,
			{ provinces: provinces }
		);
	}).ok();
});

Bappse.route(/^provinces\/(\d+)$/, function(id) {
	$dao.province.findById(parseInt(id)).success(function(province) {
		$views.province.render(
			app, 
			{ province: province }
		);
	}).ok();
});

Bappse.route(/^provinces\/new$/, function() {
	$views.province.render(
		app, 
		{ province: {} }
	);
});

/*
 * CRUD Unit
 */
Bappse.route(/^units$/, function() {
	$dao.unit.findAll().success(function(units) {
		$views.units.render(
			app,
			{ units: units }
		);
	}).ok();
});

Bappse.route(/^units\/(\d+)$/, function(id) {
	$dao.unit.findById(parseInt(id)).success(function(unit) {
		$views.unit.render(
			app, 
			{ unit: unit }
		);
	}).ok();
});

Bappse.route(/^units\/new$/, function() {
	$views.unit.render(
		app, 
		{ unit: {} }
	);
});

/*
 * CRUD Product
 */
Bappse.route(/^products(?:\/page([0-9]+))?$/, function(page) {
	page = parseInt(page || 0);
	Bappse.AjaxQueue(
		$dao.product.findAll({
			offset: page * $config.pagination.itemsByPage,
			limit: $config.pagination.itemsByPage
		}),
		$dao.product.count()
	).success(function(products, length) {
		$views.products.render(
			app, { 
				products: products ,
				pagination: $service.pagination(
						page,
						Math.ceil(length/$config.pagination.itemsByPage),
						$config.pagination.size
				)
			}
		);
	}).ok();
});

Bappse.route(/^products\/(\d+)$/, function(id) {
	Bappse.AjaxQueue(
		$dao.product.findById(parseInt(id)),
		$dao.category.findAll(),
		$dao.unit.findAll()
	).success(function(product, categories, units) {
		$views.product.render(
			app, { 
				product: product,
				categories: categories,
				units: units
			}
		);
	}).ok();
});

Bappse.route(/^products\/new$/, function() {
	Bappse.AjaxQueue(
			$dao.category.findAll(),
			$dao.unit.findAll()
		).success(function(categories, units) {
			$views.product.render(
				app, { 
					product: {},
					categories: categories,
					units: units
				}
			);
		}).ok();
});

/*
 * CRUD Locality
 */
Bappse.route(/^localities(?:\/page([0-9]+))?$/, function(page) {
	page = parseInt(page || 0);
	Bappse.AjaxQueue(
		$dao.locality.findAll({
			offset: page * $config.pagination.itemsByPage,
			limit: $config.pagination.itemsByPage
		}),
		$dao.locality.count()
	).success(function(localities, length) {
		$views.localities.render(
			app, {
				localities: localities,
				pagination: $service.pagination(
					page,
					Math.ceil(length/$config.pagination.itemsByPage),
					$config.pagination.size
				)
			}
		);
	}).ok();
});


Bappse.route(/^localities\/(\d+)$/, function(id) {
	Bappse.AjaxQueue(
			$dao.locality.findById(parseInt(id)),
			$dao.province.findAll()
	).success(function(locality, provinces) {
		$views.locality.render(
			app, { 
				locality: locality,
				provinces: provinces
			}
		);
	}).ok();
});

Bappse.route(/^localities\/new$/, function() {
	$dao.province.findAll().success(function(provinces) {
		$views.locality.render(
			app, { 
				locality: {},
				provinces: provinces
			}
		);
	}).ok();
});

/*
 * CRUD Locality
 */
Bappse.route(/^departments(?:\/page([0-9]+))?$/, function(page) {
	page = parseInt(page || 0);
	Bappse.AjaxQueue(
		$dao.department.findAll({
			offset: page * $config.pagination.itemsByPage,
			limit: $config.pagination.itemsByPage
		}),
		$dao.department.count()
	).success(function(departments, length) {
		$views.departments.render(
			app, {
				departments: departments,
				pagination: $service.pagination(
					page,
					Math.ceil(length/$config.pagination.itemsByPage),
					$config.pagination.size
				)
			}
		);
	}).ok();
});


Bappse.route(/^departments\/(\d+)$/, function(id) {
	Bappse.AjaxQueue(
			$dao.department.findById(parseInt(id)),
			$dao.province.findAll()
	).success(function(department, provinces) {
		$views.department.render(
			app, { 
				department: department,
				provinces: provinces
			}
		);
	}).ok();
});

Bappse.route(/^departments\/new$/, function() {
	$dao.province.findAll().success(function(provinces) {
		$views.department.render(
			app, { 
				department: {},
				provinces: provinces
			}
		);
	}).ok();
});

/*
 * CRUD Company
 */
Bappse.route(/^companies$/, function() {
	$dao.company.findAll().success(function(companies) {
		$views.companies.render(
			app,
			{ companies: companies }
		);
	}).ok();
});

Bappse.route(/^companies\/(\d+)$/, function(id) {
	$dao.company.findById(parseInt(id)).success(function(company) {
		$views.company.render(
			app, 
			{ company: company }
		);
	}).ok();
});

Bappse.route(/^companies\/new$/, function() {
	$views.company.render(
		app, 
		{ unit: {} }
	);
});

/*
 * CRUD Firm
 */
Bappse.route(/^firms(?:\/page([0-9]+))?$/, function(page) {
	page = parseInt(page || 0);
	Bappse.AjaxQueue(
		$dao.firm.findAll({
			offset: page * $config.pagination.itemsByPage,
			limit: $config.pagination.itemsByPage
		}),
		$dao.firm.count()
	).success(function(firms, length) {
		$views.firms.render(
			app, {
				firms: firms,
				pagination: $service.pagination(
					page,
					Math.ceil(length/$config.pagination.itemsByPage),
					$config.pagination.size
				)
			}
		);
	}).ok();
});

Bappse.route(/^firms\/(\d+)$/, function(id) {
	Bappse.AjaxQueue(
		$dao.firm.findById(parseInt(id)),
		$dao.company.findAll()
	).success(function(firm, companies) {
		$views.firm.render(
			app, {
				firm: firm,
				companies: companies
		});
	}).ok();
});

Bappse.route(/^firms\/new$/, function() {
	$dao.company.findAll().success(function(companies) {
		$views.firm.render(
			app, {
				firm: {},
				companies: companies
			}
		);
	}).ok();
});

/*
 * CRUD Branch
 */
Bappse.route(/^branches(?:\/page([0-9]+))?$/, function(page) {
	page = parseInt(page || 0);
	Bappse.AjaxQueue(
		$dao.branch.findAll({
			offset: page * $config.pagination.itemsByPage,
			limit: $config.pagination.itemsByPage
		}),
		$dao.branch.count()
	).success(function(branches, length) {
		$views.branches.render(
			app, {
				branches: branches,
				pagination: $service.pagination(
					page,
					Math.ceil(length/$config.pagination.itemsByPage),
					$config.pagination.size
				)
			}
		);
	}).ok();
});

Bappse.route(/^branches\/(\d+)$/, function(id) {
	Bappse.AjaxQueue(
		$dao.branch.findById(parseInt(id)),
		$dao.company.findAll(),
		$dao.province.findAll()
	).success(function(branch, companies, provinces) {
		$views.branch.render(
			app, {
				branch: branch,
				companies: companies,
				provinces: provinces
		});
	}).ok();
});

Bappse.route(/^branches\/new$/, function() {
	Bappse.AjaxQueue(
			$dao.company.findAll(),
			$dao.province.findAll()
		).success(function(companies, provinces) {
			$views.branch.render(
				app, {
					branch: {},
					companies: companies,
					provinces: provinces
			});
		}).ok();
});


/*
 * CRUD User
 */
Bappse.route(/^users(?:\/page([0-9]+))?$/, function(page) {
	page = parseInt(page || 0);
	Bappse.AjaxQueue(
		$dao.user.findAll({
			offset: page * $config.pagination.itemsByPage,
			limit: $config.pagination.itemsByPage
		}),
		$dao.user.count()
	).success(function(users, length) {
		$views.users.render(
			app, {
				users: users,
				pagination: $service.pagination(
					page,
					Math.ceil(length/$config.pagination.itemsByPage),
					$config.pagination.size
				)
			}
		);
	}).ok();
});

Bappse.route(/^users\/(\d+)$/, function(id) {
	Bappse.AjaxQueue(
		$dao.user.findById(parseInt(id)),
		$dao.role.findAll()
	).success(function(user, roles) {
		$views.user.render(
			app, {
				user: user,
				roles: roles
		});
	}).ok();
});

Bappse.route(/^users\/new$/, function() {
	$dao.role.findAll().success(function(roles) {
			$views.user.render(
				app, {
					user: {},
					roles: roles
			});
		}).ok();
});