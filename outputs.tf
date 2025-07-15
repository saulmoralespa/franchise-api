output "rds_endpoint" {
  description = "The endpoint of the RDS instance"
  value       = aws_db_instance.mysql.endpoint
}

output "rds_db_name" {
  description = "The database name"
  value       = aws_db_instance.mysql.db_name
}

output "rds_username" {
  description = "The master username for the database"
  value       = aws_db_instance.mysql.username
}

output "connection_string" {
  description = "JDBC connection string for the database"
  value       = "jdbc:mysql://${aws_db_instance.mysql.endpoint}/${aws_db_instance.mysql.db_name}"
  sensitive   = true
}
