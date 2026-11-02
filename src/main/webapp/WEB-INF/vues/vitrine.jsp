<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vitrine</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        header { display: flex; justify-content: space-between; align-items: center; }
        table { border-collapse: collapse; width: 100%; margin-top: 15px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #7E22CE; color: white; }
        .erreur { color: #b91c1c; font-weight: bold; }
        .btn-ajouter { padding: 5px 10px; background: #C2410C; color: white;
                       text-decoration: none; border-radius: 4px; }
        .epuise { color: #888; font-style: italic; }
        .admin { margin-top: 30px; padding: 12px; background: #f3f4f6; }
        .admin input { padding: 5px; margin-right: 6px; }
    </style>
</head>
<body>
    <header>
        <h1>Vitrine des produits</h1>
        <div>
            <c:choose>
                <c:when test="${not empty sessionScope.internaute}">
                    Bonjour <c:out value="${sessionScope.internaute.prenom}"/> |
                    <a href="panier?action=afficher">Mon panier</a> |
                    <a href="client?action=deconnecter">Déconnexion</a>
                </c:when>
                <c:otherwise>
                    <a href="client">Se connecter / S'inscrire</a>
                </c:otherwise>
            </c:choose>
        </div>
    </header>

    <c:if test="${not empty erreur}">
        <p class="erreur"><c:out value="${erreur}"/></p>
    </c:if>

    <table>
        <tr>
            <th>Désignation</th><th>Description</th><th>Prix</th><th>Stock</th><th>Actions</th>
        </tr>
        <c:forEach var="p" items="${produits}">
            <tr>
                <td><c:out value="${p.designation}"/></td>
                <td><c:out value="${p.description}"/></td>
                <td>${p.prix} DH</td>
                <td>${p.stock}</td>
                <td>
                    <c:choose>
                        <c:when test="${p.stock > 0}">
                            <a class="btn-ajouter" href="panier?action=ajouter&idProduit=${p.id}">Ajouter au panier</a>
                        </c:when>
                        <c:otherwise><span class="epuise">Épuisé</span></c:otherwise>
                    </c:choose>
                    <a href="vitrine?action=supprimer&id=${p.id}"
                       onclick="return confirm('Supprimer ce produit ?');">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${empty produits}"><p>Aucun produit pour le moment.</p></c:if>

    <div class="admin">
        <h3>Ajouter un produit à la vitrine</h3>
        <form action="vitrine" method="post">
            <input type="hidden" name="action" value="ajouter">
            <input type="text" name="designation" placeholder="Désignation" required>
            <input type="text" name="description" placeholder="Description">
            <input type="number" step="0.01" name="prix" placeholder="Prix" required>
            <input type="number" name="stock" placeholder="Stock" required>
            <button type="submit">Ajouter</button>
        </form>
    </div>

    <!-- jQuery : animation au clic sur "Ajouter au panier" -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script>
        $(document).ready(function () {
            $(".btn-ajouter").on("click", function (e) {
                e.preventDefault();                       // on garde le lien pour après l'animation
                var url = $(this).attr("href");
                $(this).text("Ajouté ✓").css("background", "#15803D");
                $(this).closest("tr").fadeTo(250, 0.4).fadeTo(250, 1, function () {
                    window.location = url;
                });
            });
        });
    </script>
</body>
</html>