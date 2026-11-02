<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .cols { display: flex; gap: 60px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input { padding: 6px; width: 250px; }
        button { margin-top: 14px; padding: 8px 16px; background: #1D4ED8; color: white; border: 0; border-radius: 4px; }
        .erreur { color: #b91c1c; font-weight: bold; }
    </style>
</head>
<body>
    <h1>Espace internaute</h1>
    <c:if test="${not empty erreur}"><p class="erreur"><c:out value="${erreur}"/></p></c:if>

    <div class="cols">
        <form action="client" method="post">
            <h2>Se connecter</h2>
            <input type="hidden" name="action" value="connecter">
            <label>Email</label><input type="email" name="email" required>
            <label>Mot de passe</label><input type="password" name="motDePasse" required>
            <button type="submit">Connexion</button>
        </form>

        <form action="client" method="post">
            <h2>S'inscrire</h2>
            <input type="hidden" name="action" value="inscrire">
            <label>Nom</label><input type="text" name="nom" required>
            <label>Prénom</label><input type="text" name="prenom" required>
            <label>Email</label><input type="email" name="email" required>
            <label>Mot de passe</label><input type="password" name="motDePasse" required>
            <button type="submit">Créer mon compte</button>
        </form>
    </div>

    <p><a href="vitrine">← Retour à la vitrine</a></p>
</body>
</html>