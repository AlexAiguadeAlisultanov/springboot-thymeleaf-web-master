/* Biblioteca. Filtre, ordenació i confirmació d'esborrat.
   Tot passa al navegador: no hi ha cap petició nova ni cap ruta nova. */

(function () {
    'use strict';

    /* Filtre de text sobre les files de la taula */
    function muntaCercador(taula) {
        var camp = document.querySelector('[data-cerca="' + taula.id + '"]');
        if (!camp) return;

        var recompte = document.querySelector('[data-recompte="' + taula.id + '"]');
        var senseResultats = document.querySelector('[data-sense-resultats="' + taula.id + '"]');
        var files = Array.prototype.slice.call(taula.tBodies[0].rows);
        var total = files.length;
        /* El text de partida ja arriba traduït del servidor. Per al recompte filtrat
           el servidor deixa la plantilla a data-plantilla-filtre, amb {v} i {n}. */
        var textTotal = recompte ? recompte.textContent : '';

        function filtra() {
            var text = camp.value.trim().toLowerCase();
            var visibles = 0;

            files.forEach(function (fila) {
                var coincideix = text === '' || fila.textContent.toLowerCase().indexOf(text) !== -1;
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
                if (consulta) consulta.textContent = camp.value.trim();
            }
        }

        camp.addEventListener('input', filtra);
        camp.addEventListener('keydown', function (e) {
            if (e.key === 'Escape' && camp.value !== '') {
                camp.value = '';
                filtra();
            }
        });
    }

    /* Ordenació per columna, clicant la capçalera */
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
                /* data-valor ja porta el número tal com surt de la base de dades */
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
        muntaCercador(taula);
        if (taula.tHead) muntaOrdenacio(taula);
    });

    /* Esborrar demana confirmació abans d'anar a la ruta */
    document.querySelectorAll('[data-confirma]').forEach(function (enllac) {
        enllac.addEventListener('click', function (e) {
            if (!window.confirm(enllac.dataset.confirma)) {
                e.preventDefault();
            }
        });
    });
})();
