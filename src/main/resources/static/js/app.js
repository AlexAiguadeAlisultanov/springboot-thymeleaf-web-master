/* Biblioteca. Filtre de text, filtre per categoria, ordenacio i confirmacio d'esborrat.
   Tot passa al navegador: no hi ha cap peticio nova ni cap ruta nova. Sense JavaScript
   la taula es veu sencera i ordenada com la torna el servidor. */

(function () {
    'use strict';

    /* Filtre: text lliure i categoria es combinen, no es substitueixen */
    function muntaFiltres(taula) {
        var camp = document.querySelector('[data-cerca="' + taula.id + '"]');
        var caixaCategories = document.querySelector('[data-categories="' + taula.id + '"]');
        if (!camp && !caixaCategories) return;

        var recompte = document.querySelector('[data-recompte="' + taula.id + '"]');
        var senseResultats = document.querySelector('[data-sense-resultats="' + taula.id + '"]');
        var files = Array.prototype.slice.call(taula.tBodies[0].rows);
        var total = files.length;
        /* El text de partida ja arriba traduit del servidor. Per al recompte filtrat
           el servidor deixa la plantilla a data-plantilla-filtre, amb {v} i {n}. */
        var textTotal = recompte ? recompte.textContent : '';
        var categoria = '';

        function filtra() {
            var text = camp ? camp.value.trim().toLowerCase() : '';
            var visibles = 0;

            files.forEach(function (fila) {
                var perText = text === '' || fila.textContent.toLowerCase().indexOf(text) !== -1;
                var perCategoria = categoria === '' || (fila.dataset.categoria || '') === categoria;
                var coincideix = perText && perCategoria;
                fila.hidden = !coincideix;
                if (coincideix) visibles++;
            });

            if (recompte) {
                recompte.textContent = visibles === total
                    ? textTotal
                    : (recompte.dataset.plantillaFiltre || '{v} / {n}')
                        .replace('{v}', visibles)
                        .replace('{n}', total);
            }
            if (senseResultats) {
                senseResultats.hidden = visibles !== 0;
                var consulta = senseResultats.querySelector('[data-consulta]');
                if (consulta) consulta.textContent = camp ? camp.value.trim() : categoria;
            }
        }

        if (camp) {
            camp.addEventListener('input', filtra);
            camp.addEventListener('keydown', function (e) {
                if (e.key === 'Escape' && camp.value !== '') {
                    camp.value = '';
                    filtra();
                }
            });
        }

        /* Les categories surten de la mateixa taula, aixi que la llista sempre es la
           de debo i no cal cap consulta nova. Amb una sola categoria no aporten res. */
        if (caixaCategories) {
            var noms = [];
            files.forEach(function (fila) {
                var nom = (fila.dataset.categoria || '').trim();
                if (nom !== '' && noms.indexOf(nom) === -1) noms.push(nom);
            });
            if (noms.length > 1) {
                noms.sort(function (a, b) {
                    return a.localeCompare(b);
                });
                noms.unshift('');

                var botons = noms.map(function (nom) {
                    var boto = document.createElement('button');
                    boto.type = 'button';
                    boto.className = 'categoria-filtre';
                    boto.textContent = nom === '' ? (caixaCategories.dataset.totes || '·') : nom;
                    boto.setAttribute('aria-pressed', nom === '' ? 'true' : 'false');
                    boto.addEventListener('click', function () {
                        categoria = nom;
                        botons.forEach(function (altre) {
                            altre.setAttribute('aria-pressed', altre === boto ? 'true' : 'false');
                        });
                        filtra();
                    });
                    caixaCategories.appendChild(boto);
                    return boto;
                });
            }
        }
    }

    /* Ordenacio per columna, clicant la capcalera */
    function muntaOrdenacio(taula) {
        var cos = taula.tBodies[0];

        Array.prototype.forEach.call(taula.tHead.rows[0].cells, function (cela, index) {
            if (!cela.classList.contains('ordenable')) return;
            var boto = cela.querySelector('button');
            if (!boto) return;

            boto.addEventListener('click', function () {
                var ascendent = cela.getAttribute('aria-sort') !== 'ascending';
                var numeric = cela.dataset.tipus === 'numero';

                Array.prototype.forEach.call(taula.tHead.rows[0].cells, function (altra) {
                    altra.removeAttribute('aria-sort');
                });
                cela.setAttribute('aria-sort', ascendent ? 'ascending' : 'descending');

                var files = Array.prototype.slice.call(cos.rows);
                files.sort(function (a, b) {
                    var x = valor(a.cells[index], numeric);
                    var y = valor(b.cells[index], numeric);
                    if (x < y) return ascendent ? -1 : 1;
                    if (x > y) return ascendent ? 1 : -1;
                    return 0;
                });
                files.forEach(function (fila) {
                    cos.appendChild(fila);
                });
            });
        });

        function valor(cela, numeric) {
            var crua = cela.dataset.valor;

            if (numeric) {
                /* data-valor ja porta el numero tal com surt de la base de dades */
                var n = crua !== undefined
                    ? parseFloat(crua)
                    : parseFloat(cela.textContent.replace(/\s/g, '').replace(/\./g, '').replace(',', '.').replace(/[^0-9.\-]/g, ''));
                return isNaN(n) ? -Infinity : n;
            }

            return (crua !== undefined ? crua : cela.textContent).trim().toLowerCase();
        }
    }

    document.querySelectorAll('table.taula').forEach(function (taula) {
        if (!taula.tBodies.length || !taula.tBodies[0].rows.length) return;
        muntaFiltres(taula);
        if (taula.tHead) muntaOrdenacio(taula);
    });

    /* Esborrar demana confirmacio abans d'anar a la ruta */
    document.querySelectorAll('[data-confirma]').forEach(function (enllac) {
        enllac.addEventListener('click', function (e) {
            if (!window.confirm(enllac.dataset.confirma)) {
                e.preventDefault();
            }
        });
    });
})();
