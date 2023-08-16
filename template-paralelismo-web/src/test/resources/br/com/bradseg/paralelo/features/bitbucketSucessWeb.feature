#Auto generated Octane revision tag
@TID19101REV0.13.0 @regressivo
Feature: Confluence Sucesso web

  Scenario: Pesquisar pagina web
    Given que acesso o Bitbucket web
    When pesquiso pelo repositorio web
    Then deve aparecer o repositorio pesquisado web